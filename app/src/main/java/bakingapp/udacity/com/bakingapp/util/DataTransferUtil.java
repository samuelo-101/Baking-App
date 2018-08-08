package bakingapp.udacity.com.bakingapp.util;

import java.util.ArrayList;
import java.util.List;

import bakingapp.udacity.com.bakingapp.api.model.Ingredient;
import bakingapp.udacity.com.bakingapp.api.model.Recipe;
import bakingapp.udacity.com.bakingapp.api.model.Step;
import bakingapp.udacity.com.bakingapp.db.entity.IngredientEntity;
import bakingapp.udacity.com.bakingapp.db.entity.RecipeEntity;
import bakingapp.udacity.com.bakingapp.db.entity.StepEntity;

public class DataTransferUtil {

    public static RecipeEntity recipeEntityFromModel(Recipe recipe) {
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setId(recipe.getId());
        recipeEntity.setName(recipe.getName());
        recipeEntity.setImage(recipe.getImage());
        recipeEntity.setServings(recipe.getServings());
        return recipeEntity;
    }

    public static List<IngredientEntity> ingredientEntityListFromModelList(int recipeId, List<Ingredient> ingredients) {
        List<IngredientEntity> ingredientEntities = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            IngredientEntity ingredientEntity = new IngredientEntity();
            ingredientEntity.setIngredient(ingredient.getIngredient());
            ingredientEntity.setMeasure(ingredient.getMeasure());
            ingredientEntity.setQuantity(ingredient.getQuantity());
            ingredientEntity.setRecipeId(recipeId);
            ingredientEntities.add(ingredientEntity);
        }
        return ingredientEntities;
    }

    public static List<StepEntity> stepEntityListFromModelList(int recipeId, List<Step> steps) {
        List<StepEntity> stepEntities = new ArrayList<>();
        for (Step step : steps) {
            StepEntity stepEntity = new StepEntity();
            stepEntity.setStepId(step.getId());
            stepEntity.setDescription(step.getDescription());
            stepEntity.setShortDescription(step.getShortDescription());
            stepEntity.setThumbnailURL(step.getThumbnailURL());
            stepEntity.setVideoURL(step.getVideoURL());
            stepEntity.setRecipeId(recipeId);
            stepEntities.add(stepEntity);
        }
        return stepEntities;
    }

    public static Recipe recipeModelFromRecipeEntity(RecipeEntity recipeEntity) {
        Recipe recipe = new Recipe();
        recipe.setId(recipeEntity.getId());
        recipe.setImage(recipeEntity.getImage());
        recipe.setName(recipeEntity.getName());
        recipe.setServings(recipeEntity.getServings());
        return recipe;
    }

    public static List<Step> stepModelFromStepEntityList(List<StepEntity> stepEntities) {
        List<Step> steps = new ArrayList<>();
        for (StepEntity stepEntity : stepEntities) {
            Step step = new Step();
            step.setId(stepEntity.getId());
            step.setDescription(stepEntity.getDescription());
            step.setShortDescription(stepEntity.getShortDescription());
            step.setThumbnailURL(stepEntity.getThumbnailURL());
            step.setVideoURL(stepEntity.getVideoURL());
            steps.add(step);
        }
        return steps;
    }


    public static List<Ingredient> ingredientsModelFromEntityList(List<IngredientEntity> ingredientEntities) {
        List<Ingredient> ingredients = new ArrayList<>();
        for (IngredientEntity ingredientEntity : ingredientEntities) {
            Ingredient ingredient = new Ingredient();
            ingredient.setIngredient(ingredientEntity.getIngredient());
            ingredient.setMeasure(ingredientEntity.getMeasure());
            ingredient.setQuantity(ingredientEntity.getQuantity());
            ingredients.add(ingredient);
        }
        return ingredients;
    }

}
