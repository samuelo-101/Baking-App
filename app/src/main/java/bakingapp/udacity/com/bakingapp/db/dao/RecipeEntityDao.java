package bakingapp.udacity.com.bakingapp.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import bakingapp.udacity.com.bakingapp.db.entity.RecipeEntity;

@Dao
public interface RecipeEntityDao {

    @Query("SELECT * FROM recipe ORDER BY name")
    List<RecipeEntity> fetchAllDesired();

    @Insert
    void insert(RecipeEntity recipeEntity);

    @Query("DELETE FROM recipe")
    void deleteAll();
}
