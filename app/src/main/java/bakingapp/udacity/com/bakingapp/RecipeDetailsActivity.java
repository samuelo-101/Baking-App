package bakingapp.udacity.com.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import bakingapp.udacity.com.bakingapp.adapter.StepsListRecyclerViewAdapter;
import bakingapp.udacity.com.bakingapp.api.model.Recipe;
import bakingapp.udacity.com.bakingapp.api.model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity
        implements StepsListRecyclerViewAdapter.StepItemClickListener,
        StepListFragment.OnFragmentInteractionListener,
        StepMediaFragment.OnFragmentInteractionListener {

    private final String TAG = getClass().getName();

    public static final String ARG_RECIPE = "RecipeDetailsActivity_ARG_RECIPE";

    private Recipe recipe;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        validateRecipeExtra(savedInstanceState);

        ButterKnife.bind(this);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(recipe.getName());

        if(isTabletScreen()) {
            // Tablet
            setupUIForTabletLayout();
        } else {
            setupUIForNonTabletDisplay();
        }
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

    private boolean isTabletScreen() {
        return findViewById(R.id.frameLayout_step_list_fragment) != null && findViewById(R.id.frameLayout_step_media_fragment) != null;
    }

    private void setupUIForTabletLayout() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_step_list_fragment, StepListFragment.newInstance(recipe));
        fragmentTransaction.commit();
    }

    private void setupUIForNonTabletDisplay() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_fragment_container, StepListFragment.newInstance(recipe));
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(Step step) {
        Intent intent = new Intent(getApplicationContext(), StepActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(StepActivity.ARG_STEP, step);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onFragmentStepClick(Step step) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_step_media_fragment, StepMediaFragment.newInstance(step));
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
