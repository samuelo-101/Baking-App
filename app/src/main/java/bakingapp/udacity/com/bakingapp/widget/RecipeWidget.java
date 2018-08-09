package bakingapp.udacity.com.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import java.util.List;

import bakingapp.udacity.com.bakingapp.MainActivity;
import bakingapp.udacity.com.bakingapp.R;
import bakingapp.udacity.com.bakingapp.RecipeDetailsActivity;
import bakingapp.udacity.com.bakingapp.api.model.Recipe;
import bakingapp.udacity.com.bakingapp.db.entity.RecipeEntity;
import bakingapp.udacity.com.bakingapp.db.entity.StepEntity;
import bakingapp.udacity.com.bakingapp.db.repo.ManageDesiredRecipeRepository;
import bakingapp.udacity.com.bakingapp.db.repo.dto.FetchDesiredRecipeDTO;
import bakingapp.udacity.com.bakingapp.util.DialogUtil;
import bakingapp.udacity.com.bakingapp.widget.service.RecipeWidgetService;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, FetchDesiredRecipeDTO fetchDesiredRecipeDTO) {

        String recipeName = (fetchDesiredRecipeDTO != null && fetchDesiredRecipeDTO.getRecipe() != null) ? fetchDesiredRecipeDTO.getRecipe().getName() : context.getString(R.string.you_have_no_desired_recipe_selected);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setTextViewText(R.id.textView_recipe_name, recipeName);

        Intent intent = new Intent(context, RecipeWidgetService.class);
        views.setRemoteAdapter(R.id.listView_ingredients, intent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {

        ManageDesiredRecipeRepository manageDesiredRecipeRepository = new ManageDesiredRecipeRepository(context, new ManageDesiredRecipeRepository.DatabaseOperationCallback() {
            @Override
            public void onSaveDesiredRecipeSuccess() {

            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onGetDesiredRecipeSuccess(RecipeEntity recipeEntity) {

            }

            @Override
            public void onGetDesiredRecipeStepsSuccess(List<StepEntity> stepEntities) {

            }

            @Override
            public void onGetDesiredRecipeModelWithStepsAndIngredientsSuccess(FetchDesiredRecipeDTO fetchDesiredRecipeDTO) {
                for (int appWidgetId : appWidgetIds) {
                    updateAppWidget(context, appWidgetManager, appWidgetId, fetchDesiredRecipeDTO);
                }
            }

            @Override
            public void onError(String message) {
                DialogUtil.showAlertDialogMessage(context, context.getString(R.string.api_generic_error_title), message);
            }
        });

        manageDesiredRecipeRepository.getDesiredRecipeModelWithStepsAndIngredients();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action != null && action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context, RecipeWidget.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listView_ingredients);
            this.onUpdate(context, appWidgetManager, appWidgetIds);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {

    }
}

