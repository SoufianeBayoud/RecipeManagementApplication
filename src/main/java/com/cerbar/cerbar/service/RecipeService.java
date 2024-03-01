package com.cerbar.cerbar.service;

import com.cerbar.cerbar.model.Ingredient;
import com.cerbar.cerbar.model.Recipe;

import java.util.List;

public interface RecipeService {

    public List<Recipe> getAllRecipe();

    public  List<Recipe> getAllRecipeSortedByField(String field);

    public Recipe getRecipeById(Long id);
    public Recipe saveRecipe(Recipe recipe);

    public Recipe updateRecipe(Recipe recipe);

    void deleteAllRecipe();

    void deleteRecipeById(Long id);

    public Recipe addIngredientToRecipe(Long recipeId, Ingredient ingredient) throws Exception;

    public Recipe removeIngredientFromRecipe(Long recipeId, Long ingredientId);

}
