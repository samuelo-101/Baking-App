package bakingapp.udacity.com.bakingapp.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import bakingapp.udacity.com.bakingapp.db.entity.IngredientEntity;
import bakingapp.udacity.com.bakingapp.db.entity.RecipeEntity;

@Dao
public interface IngredientEntityDao {

    @Query("SELECT * FROM ingredient")
    List<IngredientEntity> fetchAll();

    @Insert
    void insertAll(IngredientEntity... ingredientEntities);

    @Query("DELETE FROM ingredient")
    void deleteAll();
}
