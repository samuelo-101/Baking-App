package bakingapp.udacity.com.bakingapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import bakingapp.udacity.com.bakingapp.db.dao.IngredientEntityDao;
import bakingapp.udacity.com.bakingapp.db.dao.RecipeEntityDao;
import bakingapp.udacity.com.bakingapp.db.dao.StepEntityDao;
import bakingapp.udacity.com.bakingapp.db.entity.IngredientEntity;
import bakingapp.udacity.com.bakingapp.db.entity.RecipeEntity;
import bakingapp.udacity.com.bakingapp.db.entity.StepEntity;

@Database(entities = { RecipeEntity.class, StepEntity.class, IngredientEntity.class}, version = 1, exportSchema = false)
public abstract class RecipeDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "bakingapp_udacity_db";

    private static RecipeDatabase recipeDatabase;

    public abstract RecipeEntityDao recipeEntityDao();
    public abstract IngredientEntityDao ingredientEntityDao();
    public abstract StepEntityDao stepEntityDao();

    public static synchronized RecipeDatabase getInstance(Context context) {
        if(recipeDatabase == null) {
            recipeDatabase = Room.databaseBuilder(context, RecipeDatabase.class, DATABASE_NAME).build();
        }
        return recipeDatabase;
    }

}
