package bakingapp.udacity.com.bakingapp.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import bakingapp.udacity.com.bakingapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsListRecyclerViewAdapterViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textView_ingredient)
    public TextView mTextViewIngredientsDisplay;

    @BindView(R.id.textView_quantity_measure)
    public TextView mTextViewQuantityMeasure;

    public IngredientsListRecyclerViewAdapterViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
