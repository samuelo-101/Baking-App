package bakingapp.udacity.com.bakingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bakingapp.udacity.com.bakingapp.R;
import bakingapp.udacity.com.bakingapp.api.model.Ingredient;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsListRecyclerViewAdapter extends RecyclerView.Adapter<IngredientsListRecyclerViewAdapter.ViewHolder> {

    private final List<Ingredient> mIngredients;

    public IngredientsListRecyclerViewAdapter(List<Ingredient> ingredients) {
        this.mIngredients = ingredients;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ingredient ingredient = mIngredients.get(position);
        holder.mTextViewIngredientsDisplay.setText(ingredient.getIngredient());
        holder.mTextViewQuantityMeasure.setText(new StringBuilder().append(ingredient.getQuantity()).append(" ").append(ingredient.getMeasure()).toString());
    }

    @Override
    public int getItemCount() {
        return mIngredients == null ? 0 : mIngredients.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView_ingredient)
        TextView mTextViewIngredientsDisplay;

        @BindView(R.id.textView_quantity_measure)
        TextView mTextViewQuantityMeasure;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
