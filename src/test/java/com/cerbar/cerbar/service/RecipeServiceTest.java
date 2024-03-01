package com.cerbar.cerbar.service;

import com.cerbar.cerbar.model.Ingredient;
import com.cerbar.cerbar.model.Recipe;
import com.cerbar.cerbar.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {
    @InjectMocks
    private RecipeServiceImplementation recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    private Recipe recipe;

    @BeforeEach
    void init(){
        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Spaghetti");
        recipe.setDescription("This is a pasta dish");
        recipe.setInstruction("1. 2. 3. 4. 5.");

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        ingredient1.setName("Pasta");
        ingredient1.setQuantity("2 cups");
        ingredient1.setRecipe(recipe);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        ingredient2.setName("Tomato Sauce");
        ingredient2.setQuantity("1 cup");
        ingredient2.setRecipe(recipe);

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);

        recipe.setIngredients(ingredients);
    }

    @Test
    void saveRecipes(){
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        Recipe savedRecipe = recipeService.saveRecipe(recipe);
        assertNotNull(savedRecipe);
        assertThat(savedRecipe.getName()).isEqualTo("Spaghetti");
    }

    @Test
    void getAllRecipes() {
        List<Recipe> list = new ArrayList<>();
        list.add(recipe);
        list.add(recipe);
        list.add(recipe);

        when(recipeService.getAllRecipe()).thenReturn(list);
        List<Recipe> listOfRecipes = recipeService.getAllRecipe();
        assertEquals(3, listOfRecipes.size());
        assertNotNull(listOfRecipes);
    }


    @Test
    void getRecipeById(){
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        Recipe existingRecipe = recipeService.getRecipeById(1L);
        assertNotNull(existingRecipe);
        assertThat(existingRecipe.getId()).isEqualTo(1L);
    }

    @Test
        void updateRecipe(){
        recipe.setName("Penne arabiata");
        recipe.setId(2L);
        recipe.setDescription("This is ia pasta dish");
        recipe.setInstruction("1. 2. 3.i 4. 5.");
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe); //Pourquoi on doit Mock celui la ?
        Recipe updatedMovie = recipeService.updateRecipe(recipe);

        assertNotNull(updatedMovie);
        assertEquals("Penne arabiata", updatedMovie.getName());
    }

    @Test
    void deleteRecipe(){
        doNothing().when(recipeRepository).deleteById(anyLong());
        recipeService.deleteRecipeById(1L);
        verify(recipeRepository, times(1)).deleteById(1L);
    }

    @Test
    void addIngredientToRecipe() throws Exception {
        Recipe addRecipe = new Recipe();
        addRecipe.setId(1L);
        addRecipe.setName("Steak with potatoes");
        addRecipe.setInstruction("Use the BBQ");
        addRecipe.setDescription("This a dish for the summer");

        when(recipeRepository.findById(1L)).thenReturn(Optional.of(addRecipe));

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);
        ingredient3.setName("Salt");
        ingredient3.setQuantity("3 pinch");
        ingredient3.setRecipe(addRecipe);

        recipeService.addIngredientToRecipe(1L, ingredient3);

        assertNotNull(addRecipe.getIngredients());
        assertEquals(1, addRecipe.getIngredients().size());
        assertEquals("Salt", addRecipe.getIngredients().get(0).getName());

    }

    @Test
    void deleteIngredientFromRecipe(){
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(recipe)).thenReturn(recipe);

        Recipe updatedRecipe = recipeService.removeIngredientFromRecipe(1L, 1L);

        assertEquals(1, updatedRecipe.getIngredients().size());
        assertEquals(2L, updatedRecipe.getIngredients().get(0).getId()); // Check if correct ingredient remains

    }
}
