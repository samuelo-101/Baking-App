package bakingapp.udacity.com.bakingapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bakingapp.udacity.com.bakingapp.MainActivity;
import bakingapp.udacity.com.bakingapp.R;
import bakingapp.udacity.com.bakingapp.adapter.IngredientsListRecyclerViewAdapter;
import bakingapp.udacity.com.bakingapp.api.model.Ingredient;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngredientsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientsFragment extends Fragment {

    private static final String ARG_INGREDIENTS_LIST = "IngredientsFragment_ARG_INGREDIENTS_LIST";

    private List<Ingredient> ingredients;
    private IngredientsListRecyclerViewAdapter adapter;

    @BindView(R.id.recyclerView_ingredients)
    RecyclerView mRecyclerViewIngredients;

    public IngredientsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param ingredients ArrayList of Ingredients.
     * @return A new instance of fragment IngredientsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IngredientsFragment newInstance(ArrayList<Ingredient> ingredients) {
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_INGREDIENTS_LIST, ingredients);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validateIngredientsArgument();
    }

    private void validateIngredientsArgument() {
        if (getArguments() != null) {
            ingredients = getArguments().getParcelableArrayList(ARG_INGREDIENTS_LIST);
            adapter = new IngredientsListRecyclerViewAdapter(ingredients);
        } else {
            Intent intent = new Intent(getContext(), MainActivity.class);
            getActivity().startActivity(intent);
            getActivity().finish();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this, view);

        mRecyclerViewIngredients.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewIngredients.setAdapter(adapter);

        return view;
    }
}
