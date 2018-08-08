package bakingapp.udacity.com.bakingapp.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "step", foreignKeys = @ForeignKey(entity = RecipeEntity.class,
        parentColumns = "id",
        childColumns = "recipe_id"), indices = @Index(value = "recipe_id"))
public class StepEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "step_id")
    private int stepId;

    @ColumnInfo(name = "short_description")
    private String shortDescription;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "video_url")
    private String videoURL;

    @ColumnInfo(name = "thumbnail_url")
    private String thumbnailURL;

    @ColumnInfo(name = "recipe_id")
    private int recipeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
}
