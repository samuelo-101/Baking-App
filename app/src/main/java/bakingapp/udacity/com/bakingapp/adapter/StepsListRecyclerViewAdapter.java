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

import com.squareup.picasso.Picasso;

import java.util.List;

import bakingapp.udacity.com.bakingapp.R;
import bakingapp.udacity.com.bakingapp.api.model.Ingredient;
import bakingapp.udacity.com.bakingapp.api.model.Recipe;
import bakingapp.udacity.com.bakingapp.api.model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsListRecyclerViewAdapter extends RecyclerView.Adapter {

    private final int INGREDIENTS_VIEW_TYPE = 0;
    private final int HEADING_VIEW_TYPE = 1;
    private final int STEPS_VIEW_TYPE = 2;

    private final Context mContext;
    private final Recipe recipe;
    private final List<Step> steps;
    private final StepItemClickListener mStepItemClickListener;

    public StepsListRecyclerViewAdapter(Context context, Recipe recipe, StepItemClickListener stepItemClickListener) {
        this.mContext = context;
        this.recipe = recipe;
        this.steps = recipe.getSteps();
        this.mStepItemClickListener = stepItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case INGREDIENTS_VIEW_TYPE: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_ingredients_layout_navigator, parent, false);
                return new ViewHolderIngredientsOption(view);
            }
            case HEADING_VIEW_TYPE: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_list_heading, parent, false);
                return new ViewHolderStepsHeading(view);
            }
            default: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_step_item, parent, false);
                return new ViewHolderSteps(view);
            }
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);

        switch (itemViewType) {
            case INGREDIENTS_VIEW_TYPE: {
                ViewHolderIngredientsOption viewHolder = (ViewHolderIngredientsOption) holder;
                if (!TextUtils.isEmpty(recipe.getImage())) {
                    Picasso.with(mContext)
                            .load(recipe.getImage())
                            .placeholder(R.drawable.dough)
                            .error(R.drawable.ic_broken_grey)
                            .into(viewHolder.mImageViewRecipeImage);
                    viewHolder.mTextViewNoImageMessage.setVisibility(View.GONE);
                }

                viewHolder.mCoordinatorLayoutContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mStepItemClickListener.onIngredientsClick(recipe.getIngredients());
                    }
                });

                viewHolder.mImageButtonNavIngredients.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mStepItemClickListener.onIngredientsClick(recipe.getIngredients());
                    }
                });
                break;
            }
            case HEADING_VIEW_TYPE: {
                ViewHolderStepsHeading viewHolder = (ViewHolderStepsHeading) holder;
                viewHolder.mTextViewStepsHeading.setText(mContext.getString(R.string.steps));
                break;
            }
            case STEPS_VIEW_TYPE: {
                final ViewHolderSteps viewHolder = (ViewHolderSteps) holder;
                final Step step = steps.get(position - 2);

                if (step.getId() > 0) {
                    viewHolder.mTextViewStepId.setText(new StringBuilder().append(mContext.getString(R.string.step)).append(" ").append(step.getId()).toString());
                } else {
                    viewHolder.mTextViewStepId.setText(step.getShortDescription());
                }

                viewHolder.mImageViewStepDone.setVisibility(View.GONE);

                if (!TextUtils.isEmpty(step.getThumbnailURL())) {
                    Picasso.with(mContext)
                            .load(step.getThumbnailURL())
                            .placeholder(R.drawable.ic_image_grey)
                            .error(R.drawable.ic_broken_grey)
                            .into(viewHolder.mImageViewStepImage);
                    viewHolder.mTextViewImageUnavailableMessage.setVisibility(View.GONE);
                }

                viewHolder.mTextViewShortDescription.setText(step.getShortDescription());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewHolder.mImageViewStepDone.setVisibility(View.VISIBLE);
                        mStepItemClickListener.onStepClick(step);
                    }
                });
                break;
            }
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

    class ViewHolderSteps extends RecyclerView.ViewHolder {

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

        ViewHolderSteps(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ViewHolderStepsHeading extends RecyclerView.ViewHolder {

        @BindView(R.id.textView_steps_label)
        TextView mTextViewStepsHeading;

        ViewHolderStepsHeading(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ViewHolderIngredientsOption extends RecyclerView.ViewHolder {

        @BindView(R.id.coordinatorLayout_container)
        ConstraintLayout mCoordinatorLayoutContainer;

        @BindView(R.id.imageView_recipe_image)
        ImageView mImageViewRecipeImage;

        @BindView(R.id.textView_image_unavailable)
        TextView mTextViewNoImageMessage;

        @BindView(R.id.imageButton_view_ingredients)
        ImageButton mImageButtonNavIngredients;

        ViewHolderIngredientsOption(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface StepItemClickListener {
        void onStepClick(Step step);
        void onIngredientsClick(List<Ingredient> ingredients);
    }
}
