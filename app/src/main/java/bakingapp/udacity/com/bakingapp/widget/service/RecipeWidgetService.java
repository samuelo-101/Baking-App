package bakingapp.udacity.com.bakingapp.widget.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import bakingapp.udacity.com.bakingapp.widget.factory.RecipeWidgetRemoteViewsFactory;

public class RecipeWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeWidgetRemoteViewsFactory(getApplicationContext());
    }
}
