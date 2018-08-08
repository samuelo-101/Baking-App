package bakingapp.udacity.com.bakingapp.db.repo.dto;

import java.util.List;

import bakingapp.udacity.com.bakingapp.api.model.Ingredient;
import bakingapp.udacity.com.bakingapp.api.model.Recipe;
import bakingapp.udacity.com.bakingapp.api.model.Step;

public class FetchDesiredRecipeDTO {

    private Recipe recipe;
    private List<Step> steps;
    private List<Ingredient> ingredients;

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
