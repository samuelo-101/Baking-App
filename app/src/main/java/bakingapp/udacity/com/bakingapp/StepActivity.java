package bakingapp.udacity.com.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import bakingapp.udacity.com.bakingapp.api.model.Step;

public class StepActivity extends AppCompatActivity implements StepMediaFragment.OnFragmentInteractionListener {
    private final String TAG = getClass().getName();

    public static final String ARG_STEP = "StepActivity_ARG_STEP";

    private Step step;

    private Toolbar mToolBar;

    private boolean isTabletScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        validateStepExtra();

        mToolBar = findViewById(R.id.toolbar);

        if(findViewById(R.id.frameLayout_step_media_fragment) != null) {
            // Tablet
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout_step_media_fragment, StepMediaFragment.newInstance(step));
            fragmentTransaction.commit();
        } else {
            if (mToolBar != null) {
                // Portrait mode
                setSupportActionBar(mToolBar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                setupUIForPortraitMode();
            } else {
                // Landscape mode (Media player full screen)


            }
        }
    }

    private void validateStepExtra() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(ARG_STEP)) {
            step = extras.getParcelable(ARG_STEP);
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void setupUIForPortraitMode() {
        if(step.getId() > 0) {
            setTitle(new StringBuilder().append(getString(R.string.step)).append(" ").append(step.getId()));
        } else {
            setTitle(step.getDescription());
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
