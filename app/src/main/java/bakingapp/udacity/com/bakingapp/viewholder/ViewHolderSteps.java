package bakingapp.udacity.com.bakingapp.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import bakingapp.udacity.com.bakingapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewHolderSteps extends RecyclerView.ViewHolder {

    @BindView(R.id.textView_step_id)
    public TextView mTextViewStepId;

    @BindView(R.id.imageView_recipe_image)
    public ImageView mImageViewStepImage;

    @BindView(R.id.textView_image_unavailable)
    public TextView mTextViewImageUnavailableMessage;

    @BindView(R.id.textView_short_description)
    public TextView mTextViewShortDescription;

    @BindView(R.id.imageView_check)
    public ImageView mImageViewStepDone;

    public ViewHolderSteps(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
