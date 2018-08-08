package bakingapp.udacity.com.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import bakingapp.udacity.com.bakingapp.api.model.Ingredient;
import bakingapp.udacity.com.bakingapp.api.model.Recipe;
import bakingapp.udacity.com.bakingapp.api.model.Step;
import bakingapp.udacity.com.bakingapp.fragment.IngredientsFragment;
import bakingapp.udacity.com.bakingapp.fragment.StepListFragment;
import bakingapp.udacity.com.bakingapp.fragment.StepMediaFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity implements
        StepListFragment.OnFragmentInteractionListener,
        StepMediaFragment.OnFragmentInteractionListener {

    private final String TAG = getClass().getName();

    public static final String ARG_RECIPE = "RecipeDetailsActivity_ARG_RECIPE";
    public static final String ARG_RECIPE_IS_DESIRED = "RecipeDetailsActivity_ARG_RECIPE_IS_DESIRED";

    private Recipe recipe;
    private boolean isRecipeDesired;

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
        if (extras != null && extras.containsKey(ARG_RECIPE) && extras.containsKey(ARG_RECIPE_IS_DESIRED)) {
            recipe = extras.getParcelable(ARG_RECIPE);
            isRecipeDesired = extras.getBoolean(ARG_RECIPE_IS_DESIRED);
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
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setupUIForNonTabletDisplay() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_fragment_container, StepListFragment.newInstance(recipe));
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentStepClick(Step step) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(isTabletScreen()) {
            fragmentTransaction.replace(R.id.frameLayout_step_media_fragment, StepMediaFragment.newInstance(step));
        } else {
            fragmentTransaction.replace(R.id.frameLayout_fragment_container, StepMediaFragment.newInstance(step));
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentIngredientsNavigationClick(List<Ingredient> ingredients) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(isTabletScreen()) {
            fragmentTransaction.replace(R.id.frameLayout_step_media_fragment, IngredientsFragment.newInstance(new ArrayList<>(ingredients)));
        } else {
            fragmentTransaction.replace(R.id.frameLayout_fragment_container, IngredientsFragment.newInstance(new ArrayList<>(ingredients)));
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void setupDisplay(boolean isFullScreen) {
        if(isFullScreen && getSupportActionBar().isShowing()) {
            getSupportActionBar().hide();
        } else if(!isFullScreen && ! getSupportActionBar().isShowing()) {
            getSupportActionBar().show();
        }
    }
}
