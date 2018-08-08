package bakingapp.udacity.com.bakingapp.db.repo;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import bakingapp.udacity.com.bakingapp.R;
import bakingapp.udacity.com.bakingapp.api.model.Ingredient;
import bakingapp.udacity.com.bakingapp.api.model.Recipe;
import bakingapp.udacity.com.bakingapp.api.model.Step;
import bakingapp.udacity.com.bakingapp.db.RecipeDatabase;
import bakingapp.udacity.com.bakingapp.db.dao.IngredientEntityDao;
import bakingapp.udacity.com.bakingapp.db.dao.RecipeEntityDao;
import bakingapp.udacity.com.bakingapp.db.dao.StepEntityDao;
import bakingapp.udacity.com.bakingapp.db.entity.IngredientEntity;
import bakingapp.udacity.com.bakingapp.db.entity.RecipeEntity;
import bakingapp.udacity.com.bakingapp.db.entity.StepEntity;
import bakingapp.udacity.com.bakingapp.db.repo.dto.FetchDesiredRecipeDTO;
import bakingapp.udacity.com.bakingapp.util.DataTransferUtil;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class ManageDesiredRecipeRepository {

    private final Context mContext;
    private final DatabaseOperationCallback mDatabaseOperationCallback;

    private final RecipeEntityDao recipeEntityDao;
    private final IngredientEntityDao ingredientEntityDao;
    private final StepEntityDao stepEntityDao;

    public ManageDesiredRecipeRepository(Context mContext, DatabaseOperationCallback mDatabaseOperationCallback) {
        this.mContext = mContext;
        this.mDatabaseOperationCallback = mDatabaseOperationCallback;
        RecipeDatabase recipeDatabase = RecipeDatabase.getInstance(mContext);
        this.recipeEntityDao = recipeDatabase.recipeEntityDao();
        this.ingredientEntityDao = recipeDatabase.ingredientEntityDao();
        this.stepEntityDao = recipeDatabase.stepEntityDao();
    }

    public void saveDesiredRecipe(final RecipeEntity recipeEntity, final List<IngredientEntity> ingredientEntities, final List<StepEntity> stepEntities) {
        Completable.fromAction(new Action() {
            @Override
            public void run() {
                stepEntityDao.deleteAll();
                ingredientEntityDao.deleteAll();
                recipeEntityDao.deleteAll();

                recipeEntityDao.insert(recipeEntity);
                ingredientEntityDao.insertAll(ingredientEntities.toArray(new IngredientEntity[ingredientEntities.size()]));
                stepEntityDao.insertAll(stepEntities.toArray(new StepEntity[stepEntities.size()]));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        mDatabaseOperationCallback.onSaveDesiredRecipeSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mDatabaseOperationCallback.onError(mContext.getResources().getString(R.string.error_db_query_insert_failed));
                    }
                });
    }

    public void getDesiredRecipe() {
        Completable.fromAction(new Action() {
            @Override
            public void run() {
                List<RecipeEntity> recipeEntities = recipeEntityDao.fetchAllDesired();
                RecipeEntity recipeEntity = (recipeEntities == null || recipeEntities.size() != 1) ? null : recipeEntities.get(0);
                mDatabaseOperationCallback.onGetDesiredRecipeSuccess(recipeEntity);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mDatabaseOperationCallback.onError(mContext.getResources().getString(R.string.error_db_query_insert_failed));
                    }
                });
    }

    public void getDesiredRecipeSteps() {
        Completable.fromAction(new Action() {
            @Override
            public void run() {
                List<StepEntity> recipeEntities = stepEntityDao.fetchAll();
                mDatabaseOperationCallback.onGetDesiredRecipeStepsSuccess(recipeEntities);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mDatabaseOperationCallback.onError(mContext.getResources().getString(R.string.error_db_query_insert_failed));
                    }
                });
    }

    public void getDesiredRecipeModelWithStepsAndIngredients() {
        Completable.fromAction(new Action() {
            @Override
            public void run() {
                List<RecipeEntity> recipeEntities = recipeEntityDao.fetchAllDesired();
                List<StepEntity> stepEntities = stepEntityDao.fetchAll();
                List<IngredientEntity> ingredientEntities = ingredientEntityDao.fetchAll();

                FetchDesiredRecipeDTO fetchDesiredRecipeDTO = null;
                if(recipeEntities != null && recipeEntities.size() == 1) {
                    fetchDesiredRecipeDTO = new FetchDesiredRecipeDTO();
                    fetchDesiredRecipeDTO.setRecipe(DataTransferUtil.recipeModelFromRecipeEntity(recipeEntities.get(0)));
                    fetchDesiredRecipeDTO.setIngredients(DataTransferUtil.ingredientsModelFromEntityList(ingredientEntities));
                    fetchDesiredRecipeDTO.setSteps(DataTransferUtil.stepModelFromStepEntityList(stepEntities));
                }

                mDatabaseOperationCallback.onGetDesiredRecipeModelWithStepsAndIngredientsSuccess(fetchDesiredRecipeDTO);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mDatabaseOperationCallback.onError(mContext.getResources().getString(R.string.error_db_query_insert_failed));
                    }
                });

    }

    public interface DatabaseOperationCallback {
        void onSaveDesiredRecipeSuccess();
        void onSuccess();
        void onGetDesiredRecipeSuccess(RecipeEntity recipeEntity);
        void onGetDesiredRecipeStepsSuccess(List<StepEntity> stepEntities);
        void onGetDesiredRecipeModelWithStepsAndIngredientsSuccess(FetchDesiredRecipeDTO fetchDesiredRecipeDTO);
        void onError(String message);
    }

}
