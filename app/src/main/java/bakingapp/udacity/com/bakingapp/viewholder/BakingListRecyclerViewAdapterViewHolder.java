package bakingapp.udacity.com.bakingapp.viewholder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import bakingapp.udacity.com.bakingapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BakingListRecyclerViewAdapterViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textView_name)
    public TextView mTextViewName;

    @BindView(R.id.constraintLayout_container)
    public ConstraintLayout mConstraintLayoutContainer;

    @BindView(R.id.imageButton_make_recipe_desired)
    public ImageButton mImageButtonMakeRecipeDesired;

    public BakingListRecyclerViewAdapterViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
