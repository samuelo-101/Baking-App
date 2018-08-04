package bakingapp.udacity.com.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Arrays;

import bakingapp.udacity.com.bakingapp.adapter.StepsListRecyclerViewAdapter;
import bakingapp.udacity.com.bakingapp.api.model.Recipe;
import bakingapp.udacity.com.bakingapp.api.model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity implements StepsListRecyclerViewAdapter.StepItemClickListener {

    private final String TAG = getClass().getName();

    public static final String ARG_RECIPE = "RecipeDetailsActivity_ARG_RECIPE";

    private Recipe recipe;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.coordinatorLayout_container)
    ConstraintLayout mCoordinatorLayoutContainer;

    @BindView(R.id.imageView_recipe_image)
    ImageView mImageViewRecipeImage;

    @BindView(R.id.textView_image_unavailable)
    TextView mTextViewNoImageMessage;

    @BindView(R.id.recyclerView_recipe_steps)
    RecyclerView mRecyclerViewSteps;

    private StepsListRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        validateRecipeExtra(savedInstanceState);

        ButterKnife.bind(this);

        setSupportActionBar(mToolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(recipe.getName());

        setupUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_RECIPE, recipe);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void validateRecipeExtra(Bundle savedInstanceState) {
        Bundle extras = savedInstanceState == null ? getIntent().getExtras() : savedInstanceState;
        if (extras != null && extras.containsKey(ARG_RECIPE)) {
            recipe = extras.getParcelable(ARG_RECIPE);
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void setupUI() {
        mCoordinatorLayoutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if(!TextUtils.isEmpty(recipe.getImage())) {
            Picasso.with(getApplicationContext())
                    .load(recipe.getImage())
                    .placeholder(R.drawable.dough)
                    .error(R.drawable.ic_broken_grey)
                    .into(mImageViewRecipeImage);

            mTextViewNoImageMessage.setVisibility(View.GONE);
        }

        mRecyclerViewSteps.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerViewSteps.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        adapter = new StepsListRecyclerViewAdapter(getApplicationContext(), recipe.getSteps(), this);
        mRecyclerViewSteps.setAdapter(adapter);
    }

    @Override
    public void onClick(Step step) {
        Intent intent = new Intent(getApplicationContext(), StepActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(StepActivity.ARG_STEP, step);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
