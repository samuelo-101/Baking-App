package bakingapp.udacity.com.bakingapp.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bakingapp.udacity.com.bakingapp.R;
import bakingapp.udacity.com.bakingapp.api.model.Ingredient;
import bakingapp.udacity.com.bakingapp.api.model.Recipe;
import bakingapp.udacity.com.bakingapp.api.model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsListRecyclerViewAdapter extends RecyclerView.Adapter<StepsListRecyclerViewAdapter.ViewHolder> {

    private final int INGREDIENTS_VIEW_TYPE = 0;
    private final int HEADING_VIEW_TYPE = 1;
    private final int STEPS_VIEW_TYPE = 2;

    private Context mContext;
    private Recipe recipe;
    private List<Step> steps;
    private StepItemClickListener mStepItemClickListener;

    public StepsListRecyclerViewAdapter(Context context, Recipe recipe, StepItemClickListener stepItemClickListener) {
        this.mContext = context;
        this.recipe = recipe;
        this.steps = recipe.getSteps();
        this.mStepItemClickListener = stepItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if(viewType == INGREDIENTS_VIEW_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_ingredients_layout_navigator, parent, false);
        } else if(viewType == HEADING_VIEW_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_list_heading, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_step_item, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);

        if(itemViewType == INGREDIENTS_VIEW_TYPE) {
            if(!TextUtils.isEmpty(recipe.getImage())) {
                Picasso.with(mContext)
                        .load(recipe.getImage())
                        .placeholder(R.drawable.dough)
                        .error(R.drawable.ic_broken_grey)
                        .into(holder.mImageViewRecipeImage);
                holder.mTextViewNoImageMessage.setVisibility(View.GONE);
            }

            holder.mCoordinatorLayoutContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mStepItemClickListener.onIngredientsClick(recipe.getIngredients());
                }
            });

            holder.mImageButtonNavIngredients.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mStepItemClickListener.onIngredientsClick(recipe.getIngredients());
                }
            });
        } else if(itemViewType == HEADING_VIEW_TYPE) {
            holder.mTextViewStepsHeading.setText(mContext.getString(R.string.steps));
        } else if(itemViewType == STEPS_VIEW_TYPE) {
            final Step step = steps.get(position - 2);

            if (step.getId() > 0) {
                holder.mTextViewStepId.setText(new StringBuilder().append(mContext.getString(R.string.step)).append(" ").append(step.getId()).toString());
            } else {
                holder.mTextViewStepId.setText(step.getDescription());
            }

            holder.mImageViewStepDone.setVisibility(View.GONE);

            if (!TextUtils.isEmpty(step.getThumbnailURL())) {
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
                    holder.mImageViewStepDone.setVisibility(View.VISIBLE);
                    mStepItemClickListener.onStepClick(step);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.steps == null ? 2 : this.steps.size() + 2;
    }

    @Override
    public int getItemViewType(final int position) {
        if(position == 0) {
            return INGREDIENTS_VIEW_TYPE;
        } else if(position == 1) {
            return HEADING_VIEW_TYPE;
        }
        return STEPS_VIEW_TYPE;
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

        @BindView(R.id.imageView_check)
        ImageView mImageViewStepDone;

        ConstraintLayout mCoordinatorLayoutContainer;

        ImageView mImageViewRecipeImage;

        TextView mTextViewNoImageMessage;

        ImageButton mImageButtonNavIngredients;

        TextView mTextViewStepsHeading;

        ViewHolder(View itemView) {
            super(itemView);

            if(itemView.findViewById(R.id.textView_steps_label) != null) {
                mTextViewStepsHeading = itemView.findViewById(R.id.textView_steps_label);
            }

            if(itemView.findViewById(R.id.textView_step_id) != null) {
                ButterKnife.bind(this, itemView);
            }

            if(itemView.findViewById(R.id.coordinatorLayout_container) != null){
                mCoordinatorLayoutContainer = itemView.findViewById(R.id.coordinatorLayout_container);
                mImageViewRecipeImage = itemView.findViewById(R.id.imageView_recipe_image);
                mTextViewNoImageMessage = itemView.findViewById(R.id.textView_image_unavailable);
                mImageButtonNavIngredients = itemView.findViewById(R.id.imageButton_view_ingredients);
            }
        }
    }

    public interface StepItemClickListener {
        public void onStepClick(Step step);
        public void onIngredientsClick(List<Ingredient> ingredients);
    }
}
