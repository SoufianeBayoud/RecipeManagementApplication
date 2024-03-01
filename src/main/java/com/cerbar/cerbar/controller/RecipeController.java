package com.cerbar.cerbar.controller;

import com.cerbar.cerbar.model.Ingredient;
import com.cerbar.cerbar.model.Recipe;
import com.cerbar.cerbar.repository.RecipeRepository;
import com.cerbar.cerbar.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
//@RequestMapping("api/v1")
public class RecipeController {

    @Autowired
    private RecipeRepository repo;
    @Autowired
    private RecipeService recipeService;

    @GetMapping("/recipes")
    public ResponseEntity<List<Recipe>> getAllRecipes(){
        return new ResponseEntity<List<Recipe>>(recipeService.getAllRecipe(), HttpStatus.OK);
    }


    @GetMapping("/recipes/sorted/{field}")
    public ResponseEntity<List<Recipe>> getAllRecipesSortedByField(@PathVariable("field") String field){
        return new ResponseEntity<List<Recipe>>(recipeService.getAllRecipeSortedByField(field), HttpStatus.OK);
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable("id") Long id){
       return new ResponseEntity<Recipe>(recipeService.getRecipeById(id), HttpStatus.OK);
    }

    @PostMapping("/recipes")
    public ResponseEntity<Recipe> saveRecipe(@RequestBody Recipe recipe){
        //@RequestBody est obligatoire pour pouvoir changer les variables
        return new ResponseEntity<Recipe>(recipeService.saveRecipe(recipe), HttpStatus.CREATED);
    }


    @PutMapping("/recipes/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable(value = "id") Long id, @RequestBody Recipe recipe){
        recipe.setId(id);
        //J'appelle le id pour pouvoir return le update


        return new ResponseEntity<Recipe>(recipeService.updateRecipe(recipe), HttpStatus.OK);
    }

    @DeleteMapping("/recipes/deleteAll")
    public void deleteAll(){

        recipeService.deleteAllRecipe();
    }

    @DeleteMapping("/recipes")
    public void deleteById(@RequestParam("id") Long id){
        recipeService.deleteRecipeById(id);
    }


    @PutMapping("/recipes/ingredients/add/{id}")
    public ResponseEntity<Recipe> addIngredientToRecipe(@PathVariable(value = "id") Long id, @RequestBody Ingredient ingredient) throws Exception {
        //Recipe updatedRecipe = recipeService.addIngredientToRecipe(recipeId, ingredient);


        return new ResponseEntity<Recipe>(recipeService.addIngredientToRecipe(id, ingredient), HttpStatus.OK);
        //return ResponseEntity.ok(updatedRecipe);
    }

    @DeleteMapping("/recipes/ingredients/{id}/{ingredientId}")
    public ResponseEntity<Recipe> removeIngredientFromRecipe(@PathVariable Long id, @PathVariable Long ingredientId) {
        Recipe updatedRecipe = recipeService.removeIngredientFromRecipe(id, ingredientId);
        return ResponseEntity.ok(updatedRecipe);
    }








}
