package bakingapp.udacity.com.bakingapp.widget.factory;

import android.content.Context;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import bakingapp.udacity.com.bakingapp.R;
import bakingapp.udacity.com.bakingapp.db.RecipeDatabase;
import bakingapp.udacity.com.bakingapp.db.entity.IngredientEntity;

public class RecipeWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private List<IngredientEntity> mIngredients;

    public RecipeWidgetRemoteViewsFactory(Context context) {
        this.mContext = context;
        this.mIngredients = new ArrayList<>();
    }

    @Override
    public void onCreate() {
        mIngredients = new ArrayList<>();
    }

    @Override
    public void onDataSetChanged() {
        mIngredients.clear();
        mIngredients.addAll(RecipeDatabase.getInstance(mContext).ingredientEntityDao().fetchAll());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return this.mIngredients == null ? 0 : this.mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                mIngredients == null || mIngredients.size() == 0) {
            return null;
        }

        IngredientEntity ingredientEntity = mIngredients.get(position);

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);

        remoteViews.setTextViewText(R.id.textView_ingredient, ingredientEntity.getIngredient());
        remoteViews.setTextViewText(R.id.textView_quantity_measure, new StringBuilder().append(ingredientEntity.getQuantity()).append(" ").append(ingredientEntity.getMeasure()));

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return mIngredients.get(position).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
