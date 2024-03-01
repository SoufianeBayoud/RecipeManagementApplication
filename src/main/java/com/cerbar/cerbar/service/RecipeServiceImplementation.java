package com.cerbar.cerbar.service;

import com.cerbar.cerbar.model.Recipe;
import com.cerbar.cerbar.repository.RecipeRepository;
import com.cerbar.cerbar.model.Ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImplementation implements RecipeService{

    @Autowired
    private RecipeRepository recipeRepository;


    @Override
    public List<Recipe> getAllRecipe() {
        return recipeRepository.findAll();

    }

    @Override
    public List<Recipe> getAllRecipeSortedByField(String field) {
        return recipeRepository.findAll(Sort.by(Sort.Direction.ASC, field));
    }

    @Override
    public Recipe getRecipeById(Long id) {
        Optional <Recipe> rec = recipeRepository.findById(id);

        if(rec.isPresent()){
            return rec.get();
        } else {
            throw new RuntimeException("Doesn't find by id ");
        }
    }

    @Override
    public Recipe saveRecipe(Recipe recipe) {
        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredient.setRecipe(recipe);
        }

        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe updateRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);

    }


    @Override
    public void deleteAllRecipe() {
        recipeRepository.deleteAll();
    }

    @Override
    public void deleteRecipeById(Long id) {
        recipeRepository.deleteById(id);

    }

    public Recipe addIngredientToRecipe(Long recipeId, Ingredient ingredient) throws Exception {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new Exception("Recipe not found with id: " + recipeId));

        if (ingredient == null) {
            throw new Exception("Ingredient object is null.");
        }  else if (recipe.getIngredients() == null) {
            recipe.setIngredients(new ArrayList<>());
        }

        ingredient.setRecipe(recipe);
        recipe.getIngredients().add(ingredient);
        Recipe savedRecipe = recipeRepository.save(recipe);

        return savedRecipe;
    }

    public Recipe removeIngredientFromRecipe(Long recipeId, Long ingredientId) {
        Recipe recipe = getRecipeById(recipeId);
        recipe.getIngredients().removeIf(ingredient -> ingredient.getId().equals(ingredientId));
        return recipeRepository.save(recipe);
    }



}
