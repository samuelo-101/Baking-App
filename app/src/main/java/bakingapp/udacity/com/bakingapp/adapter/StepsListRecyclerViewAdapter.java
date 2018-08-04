package bakingapp.udacity.com.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bakingapp.udacity.com.bakingapp.R;
import bakingapp.udacity.com.bakingapp.api.model.Recipe;
import bakingapp.udacity.com.bakingapp.api.model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsListRecyclerViewAdapter extends RecyclerView.Adapter<StepsListRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Step> steps;
    private StepItemClickListener mStepItemClickListener;

    public StepsListRecyclerViewAdapter(Context context, List<Step> steps, StepItemClickListener stepItemClickListener) {
        this.mContext = context;
        this.steps = steps;
        this.mStepItemClickListener = stepItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_step_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Step step = steps.get(position);

        holder.mTextViewStepId.setText(new StringBuilder().append(mContext.getString(R.string.step)).append(" ").append(step.getId() + 1).toString());

        if(!TextUtils.isEmpty(step.getThumbnailURL())) {
            Picasso.with(mContext)
                    .load(step.getThumbnailURL())
                    .placeholder(R.drawable.ic_image_grey)
                    .error(R.drawable.ic_broken_grey)
                    .into(holder.mImageViewStepImage);
            holder.mTextViewImageUnavailableMessage.setVisibility(View.GONE);
        }

        holder.mTextViewShortDescription.setText(step.getShortDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStepItemClickListener.onClick(step);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.steps == null ? 0 : this.steps.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView_step_id)
        TextView mTextViewStepId;

        @BindView(R.id.imageView_recipe_image)
        ImageView mImageViewStepImage;

        @BindView(R.id.textView_image_unavailable)
        TextView mTextViewImageUnavailableMessage;

        @BindView(R.id.textView_short_description)
        TextView mTextViewShortDescription;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface StepItemClickListener {
        public void onClick(Step step);
    }
}
