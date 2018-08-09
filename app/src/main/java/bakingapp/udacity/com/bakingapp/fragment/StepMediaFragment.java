package bakingapp.udacity.com.bakingapp.fragment;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import bakingapp.udacity.com.bakingapp.MainActivity;
import bakingapp.udacity.com.bakingapp.R;
import bakingapp.udacity.com.bakingapp.api.model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepMediaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepMediaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepMediaFragment extends Fragment implements ExoPlayer.EventListener {

    private final String TAG = getClass().getName();

    public static final String ARG_STEP = "StepActivity_ARG_STEP";
    private static final String ARG_MEDIA_PLAYER_POSITION = "StepActivity_ARG_MEDIA_PLAYER_POSITION";
    private Step step;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.imageView_step_image)
    ImageView mImageViewStepImage;

    @BindView(R.id.textView_image_unavailable)
    TextView mTextViewImageUnavailableMessage;

    @BindView(R.id.textView_short_description)
    TextView mtextViewShortDescription;

    @BindView(R.id.textView_description)
    TextView mtextViewDescription;

    @BindView(R.id.exoplayer_view)
    SimpleExoPlayerView mPlayerView;

    @BindView(R.id.textView_media_unavailable)
    TextView mTextViewMediaUnavailable;

    private SimpleExoPlayer mExoPlayer;
    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;

    public StepMediaFragment() {
        // Required empty public constructor
    }


    public static StepMediaFragment newInstance(Step step) {
        StepMediaFragment fragment = new StepMediaFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_STEP, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            validateStepExtra();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step, container, false);

        if(view.findViewById(R.id.textView_description) != null) {
            mListener.setupDisplay(false);
            ButterKnife.bind(this, view);
            setupUIForPortraitOrTabletMode();
        } else {
            mListener.setupDisplay(true);
            setupUIForLandscapeMode(view);
        }

        if(savedInstanceState != null && savedInstanceState.containsKey(ARG_MEDIA_PLAYER_POSITION)) {
            mExoPlayer.seekTo(savedInstanceState.getLong(ARG_MEDIA_PLAYER_POSITION));
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mExoPlayer != null) {
            outState.putLong(ARG_MEDIA_PLAYER_POSITION, mExoPlayer.getCurrentPosition());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        mListener.setupDisplay(false);
    }

    private void validateStepExtra() {
        Bundle extras = getArguments();
        if (extras != null && extras.containsKey(ARG_STEP)) {
            step = extras.getParcelable(ARG_STEP);
        } else {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    private void setupUIForLandscapeMode(View parent) {
        mPlayerView = parent.findViewById(R.id.exoplayer_view);
        mTextViewMediaUnavailable = parent.findViewById(R.id.textView_media_unavailable);
        setupMediaPlayerViews();
    }

    private void setupUIForPortraitOrTabletMode() {
        if (!TextUtils.isEmpty(step.getThumbnailURL())) {
            Picasso.with(getContext())
                    .load(step.getThumbnailURL())
                    .error(R.drawable.ic_broken_grey)
                    .placeholder(R.drawable.dough)
                    .into(mImageViewStepImage);
            mTextViewImageUnavailableMessage.setVisibility(View.GONE);
        }

        mtextViewShortDescription.setText(step.getShortDescription());
        mtextViewDescription.setText(step.getDescription());

        setupMediaPlayerViews();
    }

    private void setupMediaPlayerViews() {
        if (!TextUtils.isEmpty(step.getVideoURL())) {
            mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.ic_play_circle_outline_grey_48));
            mTextViewMediaUnavailable.setVisibility(View.GONE);
            setupMediaPlayer();
        } else {
            mTextViewMediaUnavailable.bringToFront();
        }
    }

    private void setupMediaPlayer() {
        initializeMediaSession();
        initializePlayer(Uri.parse(step.getVideoURL()));
    }

    private void initializeMediaSession() {
        mMediaSession = new MediaSessionCompat(getContext(), TAG);
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mMediaSession.setMediaButtonReceiver(null);
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PAUSE | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                        PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());
        mMediaSession.setCallback(new BakingAppMediaSessionCallback());
        mMediaSession.setActive(true);
    }


    private void initializePlayer(Uri uri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(getContext(), getString(R.string.app_name).trim());
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }


    private void releasePlayer() {
        if(mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }


    private class BakingAppMediaSessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void setupDisplay(boolean isFullScreen);
    }
}
