package bakingapp.udacity.com.bakingapp.viewholder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import bakingapp.udacity.com.bakingapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewHolderIngredientsOption extends RecyclerView.ViewHolder {

    @BindView(R.id.coordinatorLayout_container)
    public ConstraintLayout mCoordinatorLayoutContainer;

    @BindView(R.id.imageView_recipe_image)
    public ImageView mImageViewRecipeImage;

    @BindView(R.id.textView_image_unavailable)
    public TextView mTextViewNoImageMessage;

    @BindView(R.id.imageButton_view_ingredients)
    public ImageButton mImageButtonNavIngredients;

    public ViewHolderIngredientsOption(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
