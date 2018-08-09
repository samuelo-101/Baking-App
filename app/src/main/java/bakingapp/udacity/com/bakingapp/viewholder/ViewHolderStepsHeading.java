package bakingapp.udacity.com.bakingapp.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import bakingapp.udacity.com.bakingapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewHolderStepsHeading extends RecyclerView.ViewHolder {

    @BindView(R.id.textView_steps_label)
    public TextView mTextViewStepsHeading;

    public ViewHolderStepsHeading(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}