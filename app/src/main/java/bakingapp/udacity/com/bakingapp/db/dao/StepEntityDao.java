package bakingapp.udacity.com.bakingapp.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import bakingapp.udacity.com.bakingapp.db.entity.IngredientEntity;
import bakingapp.udacity.com.bakingapp.db.entity.StepEntity;

@Dao
public interface StepEntityDao {

    @Query("SELECT * FROM step")
    List<StepEntity> fetchAll();

    @Insert
    void insertAll(StepEntity... stepEntities);

    @Query("DELETE FROM step")
    void deleteAll();
}
