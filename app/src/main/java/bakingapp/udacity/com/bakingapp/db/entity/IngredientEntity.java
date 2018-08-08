package bakingapp.udacity.com.bakingapp.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "ingredient", foreignKeys = @ForeignKey(entity = RecipeEntity.class,
        parentColumns = "id",
        childColumns = "recipe_id"), indices = @Index(value = "recipe_id"))
public class IngredientEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "quantity")
    private double quantity;

    @ColumnInfo(name = "measure")
    private String measure;

    @ColumnInfo(name = "ingredient")
    private String ingredient;

    @ColumnInfo(name = "recipe_id")
    private int recipeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
}
