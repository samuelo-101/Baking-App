package bakingapp.udacity.com.bakingapp.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bakingapp.udacity.com.bakingapp.R;
import bakingapp.udacity.com.bakingapp.api.model.Recipe;
import bakingapp.udacity.com.bakingapp.db.entity.RecipeEntity;
import bakingapp.udacity.com.bakingapp.viewholder.BakingListRecyclerViewAdapterViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BakingListRecyclerViewAdapter extends RecyclerView.Adapter<BakingListRecyclerViewAdapterViewHolder> {

    private final Context mContext;
    private List<Recipe> recipes;
    private final BakingRecipeItemClickListener mBakingRecipeItemClickListener;
    private final List<Integer> mColors;
    private RecipeEntity desiredRecipe;

    public BakingListRecyclerViewAdapter(Context context, List<Recipe> recipes, BakingRecipeItemClickListener bakingRecipeItemClickListener) {
        this.mContext = context;
        this.recipes = recipes;
        this.mBakingRecipeItemClickListener = bakingRecipeItemClickListener;
        this.mColors = new ArrayList<>();
        this.mColors.add(mContext.getResources().getColor(R.color.md_red_400));
        this.mColors.add(mContext.getResources().getColor(R.color.md_blue_400));
        this.mColors.add(mContext.getResources().getColor(R.color.md_green_400));
        this.mColors.add(mContext.getResources().getColor(R.color.md_purple_500));
    }

    @Override
    public BakingListRecyclerViewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.baking_recyclerview_item, parent, false);
        return new BakingListRecyclerViewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BakingListRecyclerViewAdapterViewHolder holder, int position) {
        final Recipe recipe = recipes.get(position);

        int backgroundColorIndex = position % this.mColors.size();

        holder.mConstraintLayoutContainer.setBackgroundColor(this.mColors.get(backgroundColorIndex));

        holder.mTextViewName.setText(recipe.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBakingRecipeItemClickListener != null) {
                    boolean isDesired = desiredRecipe != null && desiredRecipe.getId() == recipe.getId();
                    mBakingRecipeItemClickListener.onRecipeClick(recipe, isDesired);
                }
            }
        });

        if(desiredRecipe != null && desiredRecipe.getId() == recipe.getId()) {
            holder.mImageButtonMakeRecipeDesired.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_favorite_gold_48));
        } else {
            holder.mImageButtonMakeRecipeDesired.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_favorite_white_48));
        }

        holder.mImageButtonMakeRecipeDesired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBakingRecipeItemClickListener != null) {
                    mBakingRecipeItemClickListener.onMakeRecipeDesiredClick(recipe);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.recipes == null ? 0 : this.recipes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes == null ? new ArrayList<Recipe>() : recipes;
        notifyDataSetChanged();
    }

    public void updateRecipesWithDesiredRecipe(RecipeEntity desiredRecipe) {
        this.desiredRecipe = desiredRecipe;
        notifyDataSetChanged();
    }

    public interface BakingRecipeItemClickListener {
        void onRecipeClick(Recipe recipe, boolean isDesired);
        void onMakeRecipeDesiredClick(Recipe recipe);
    }
}
