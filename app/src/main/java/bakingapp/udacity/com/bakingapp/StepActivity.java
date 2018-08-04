package bakingapp.udacity.com.bakingapp;

import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
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

import bakingapp.udacity.com.bakingapp.api.model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity implements ExoPlayer.EventListener {
    private final String TAG = getClass().getName();

    public static final String ARG_STEP = "StepActivity_ARG_STEP";

    private Step step;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

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
    private NotificationManager mNotificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        validateStepExtra();

        if (findViewById(R.id.toolbar) != null) {
            ButterKnife.bind(this);
            setSupportActionBar(mToolBar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setupUIForPortraitMode();
        } else {
            setupUIForLandscapeMode();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void validateStepExtra() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(ARG_STEP)) {
            step = extras.getParcelable(ARG_STEP);
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void setupUIForLandscapeMode() {
        mPlayerView = findViewById(R.id.exoplayer_view);
        mTextViewMediaUnavailable = findViewById(R.id.textView_media_unavailable);
        setupMediaPlayerViews();
    }

    private void setupUIForPortraitMode() {
        setTitle(new StringBuilder().append(getString(R.string.step)).append(" ").append(step.getId() + 1));

        if (!TextUtils.isEmpty(step.getThumbnailURL())) {
            Picasso.with(getApplicationContext())
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
        mMediaSession = new MediaSessionCompat(this, TAG);
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
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(this, getString(R.string.app_name).trim());
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }


    private void releasePlayer() {
        //mNotificationManager.cancelAll();
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
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
}
