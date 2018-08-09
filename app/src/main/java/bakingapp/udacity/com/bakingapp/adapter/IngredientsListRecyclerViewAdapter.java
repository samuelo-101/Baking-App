package bakingapp.udacity.com.bakingapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import bakingapp.udacity.com.bakingapp.R;
import bakingapp.udacity.com.bakingapp.api.model.Ingredient;
import bakingapp.udacity.com.bakingapp.viewholder.IngredientsListRecyclerViewAdapterViewHolder;

public class IngredientsListRecyclerViewAdapter extends RecyclerView.Adapter<IngredientsListRecyclerViewAdapterViewHolder> {

    private final List<Ingredient> mIngredients;

    public IngredientsListRecyclerViewAdapter(List<Ingredient> ingredients) {
        this.mIngredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientsListRecyclerViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_list_item, parent, false);
        return new IngredientsListRecyclerViewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsListRecyclerViewAdapterViewHolder holder, int position) {
        Ingredient ingredient = mIngredients.get(position);
        holder.mTextViewIngredientsDisplay.setText(ingredient.getIngredient());
        holder.mTextViewQuantityMeasure.setText(new StringBuilder().append(ingredient.getQuantity()).append(" ").append(ingredient.getMeasure()).toString());
    }

    @Override
    public int getItemCount() {
        return mIngredients == null ? 0 : mIngredients.size();
    }

}
