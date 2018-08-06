package bakingapp.udacity.com.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import bakingapp.udacity.com.bakingapp.adapter.StepsListRecyclerViewAdapter;
import bakingapp.udacity.com.bakingapp.api.model.Ingredient;
import bakingapp.udacity.com.bakingapp.api.model.Recipe;
import bakingapp.udacity.com.bakingapp.api.model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepListFragment extends Fragment implements StepsListRecyclerViewAdapter.StepItemClickListener {

    private final String TAG = getClass().getName();

    public static final String ARG_RECIPE = "RecipeDetailsActivity_ARG_RECIPE";

    private Recipe recipe;

    @BindView(R.id.recyclerView_recipe_steps)
    RecyclerView mRecyclerViewSteps;

    private StepsListRecyclerViewAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public StepListFragment() {
        // Required empty public constructor
    }

    public static StepListFragment newInstance(Recipe recipe) {
        StepListFragment fragment = new StepListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validateRecipeExtra();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_list_fragment, container, false);
        ButterKnife.bind(this, view);

        setupUI();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void validateRecipeExtra() {
        Bundle extras = getArguments();
        if (extras != null && extras.containsKey(ARG_RECIPE)) {
            recipe = extras.getParcelable(ARG_RECIPE);
        } else {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    private void setupUI() {
        mRecyclerViewSteps.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewSteps.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        adapter = new StepsListRecyclerViewAdapter(getContext(), recipe, this);
        mRecyclerViewSteps.setAdapter(adapter);
    }


    @Override
    public void onStepClick(Step step) {
        mListener.onFragmentStepClick(step);
    }

    @Override
    public void onIngredientsClick(List<Ingredient> ingredients) {
        mListener.onFragmentIngredientsNavigationClick(ingredients);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentStepClick(Step step);
        void onFragmentIngredientsNavigationClick(List<Ingredient> ingredients);
    }
}
