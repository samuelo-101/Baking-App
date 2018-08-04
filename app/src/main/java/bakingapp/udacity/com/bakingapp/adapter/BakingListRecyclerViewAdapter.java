package bakingapp.udacity.com.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bakingapp.udacity.com.bakingapp.R;
import bakingapp.udacity.com.bakingapp.api.model.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BakingListRecyclerViewAdapter extends RecyclerView.Adapter<BakingListRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Recipe> recipes;
    private BakingRecipeItemClickListener mBakingRecipeItemClickListener;
    private List<Integer> mColors;

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.baking_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Recipe recipe = recipes.get(position);

        int backgroundColorIndex = position % this.mColors.size();

        holder.itemView.setBackgroundColor(this.mColors.get(backgroundColorIndex));

        holder.mTextViewName.setText(recipe.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBakingRecipeItemClickListener != null) {
                    mBakingRecipeItemClickListener.onClick(recipe);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.recipes == null ? 0 : this.recipes.size();
    }


    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes == null ? new ArrayList<Recipe>() : recipes;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView_name)
        TextView mTextViewName;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface BakingRecipeItemClickListener {
        void onClick(int id);
        void onClick(Recipe recipe);
    }
}
