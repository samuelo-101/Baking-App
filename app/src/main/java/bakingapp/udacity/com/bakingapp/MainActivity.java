package bakingapp.udacity.com.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import bakingapp.udacity.com.bakingapp.adapter.BakingListRecyclerViewAdapter;
import bakingapp.udacity.com.bakingapp.api.model.Recipe;
import bakingapp.udacity.com.bakingapp.api.service.BakingApiServiceHelper;
import bakingapp.udacity.com.bakingapp.util.DialogUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BakingListRecyclerViewAdapter.BakingRecipeItemClickListener {

    private final String TAG = getClass().getName();

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recyclerView_baking_list)
    RecyclerView mRecyclerViewBakingList;

    private BakingListRecyclerViewAdapter adapter;

    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolBar);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doFetchBakingRecipes();
            }
        });

        //mRecyclerViewBakingList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerViewBakingList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new BakingListRecyclerViewAdapter(this, new ArrayList<Recipe>(), this);
        mRecyclerViewBakingList.setAdapter(adapter);

        doFetchBakingRecipes();
    }

    private void doFetchBakingRecipes() {
        showLoading(true);
        disposable.add(
                getBakingApiObservable()
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @NonNull
    private DisposableSingleObserver<Response<List<Recipe>>> getBakingApiObservable() {
        return BakingApiServiceHelper.getInstance(getApplicationContext())
        .fetchRecipes()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribeWith(new DisposableSingleObserver<Response<List<Recipe>>>() {
            @Override
            public void onSuccess(Response<List<Recipe>> response) {
                handleFetchRecipesResponse(response);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                showLoading(false);
                if (e instanceof ConnectException || e instanceof UnknownHostException) {
                    DialogUtil.showAlertDialogMessage(MainActivity.this, getString(R.string.api_connection_error_title), getString(R.string.api_connection_error_message));
                } else {
                    DialogUtil.showGenericErrorMessage(MainActivity.this);
                }

            }
        });
    }

    private void handleFetchRecipesResponse(Response<List<Recipe>> response) {
        int responseCode = response.code();
        switch (responseCode) {
            case 200:
                showLoading(false);
                adapter.setRecipes(response.body());
                break;
            default:
                showLoading(false);
                adapter.setRecipes(null);
                DialogUtil.showGenericErrorMessage(MainActivity.this);
                break;
        }
    }

    private void showLoading(boolean show) {
        mSwipeRefreshLayout.setRefreshing(show);
    }


    @Override
    public void onClick(int id) {
        Log.i(TAG, String.valueOf(id));
    }

    @Override
    public void onClick(Recipe recipe) {
        Intent intent = new Intent(getApplicationContext(), RecipeDetailsActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(RecipeDetailsActivity.ARG_RECIPE, recipe);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
