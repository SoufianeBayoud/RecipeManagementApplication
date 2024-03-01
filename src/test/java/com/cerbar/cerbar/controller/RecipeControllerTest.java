package com.cerbar.cerbar.controller;

import com.cerbar.cerbar.model.Ingredient;
import com.cerbar.cerbar.model.Recipe;
import com.cerbar.cerbar.repository.RecipeRepository;
import com.cerbar.cerbar.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RecipeControllerTest {

    @MockBean
    private RecipeService recipeService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private Recipe recipe;


    @BeforeEach
    void init(){
        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Spaghetti");
        recipe.setDescription("This is a pasta dish");
        recipe.setInstruction("1. 2. 3. 4. 5.");

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setName("Pasta");
        ingredient1.setQuantity("2 cups");
        ingredient1.setRecipe(recipe);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("Tomato Sauce");
        ingredient2.setQuantity("1 cup");
        ingredient2.setRecipe(recipe);

        recipe.setIngredients(Arrays.asList(ingredient1, ingredient2));

    }


    @Test
    void shouldCreateNewRecipe() throws Exception {

        when(recipeService.saveRecipe(any(Recipe.class))).thenReturn(recipe);

        this.mockMvc.perform(post("/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipe)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(recipe.getName())))
                .andExpect(jsonPath("$.description", is(recipe.getDescription())))
                .andExpect(jsonPath("$.instruction", is(recipe.getInstruction())))
                .andExpect(jsonPath("$.ingredients[0].name", is(recipe.getIngredients().get(0).getName())))
                .andExpect(jsonPath("$.ingredients[0].quantity", is(recipe.getIngredients().get(0).getQuantity())))
                .andExpect(jsonPath("$.ingredients[1].name", is(recipe.getIngredients().get(1).getName())))
                .andExpect(jsonPath("$.ingredients[1].quantity", is(recipe.getIngredients().get(1).getQuantity())));


                //On appelle pas le TimeStamps parce que ils sont = null

    }



    @Test
    void shouldFetchAllRecipes() throws Exception {

        List<Recipe> list = Arrays.asList(recipe);

        when(recipeService.getAllRecipe()).thenReturn(list);
        this.mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(list.size())));

    }



    @Test
    void shouldFetchOneRecipeById() throws Exception {

        when(recipeService.getRecipeById(anyLong())).thenReturn(recipe);
        this.mockMvc.perform(get("/recipes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(recipe.getName())))
                .andExpect(jsonPath("$.description", is(recipe.getDescription())))
                .andExpect(jsonPath("$.instruction", is(recipe.getInstruction())))
                .andExpect(jsonPath("$.ingredients[0].name", is(recipe.getIngredients().get(0).getName())))
                .andExpect(jsonPath("$.ingredients[0].quantity", is(recipe.getIngredients().get(0).getQuantity())))
                .andExpect(jsonPath("$.ingredients[1].name", is(recipe.getIngredients().get(1).getName())))
                .andExpect(jsonPath("$.ingredients[1].quantity", is(recipe.getIngredients().get(1).getQuantity())));

    }


    @Test
    void shouldDeleteRecipeById() throws Exception {

        doNothing().when(recipeService).deleteRecipeById(anyLong());

        this.mockMvc.perform(delete("/recipes").param("id", "1"))
                .andExpect(status().isOk());
        //Si j'aurais fait passer HttpStatus.NO_CONTENT a ce moment la j'aurais mis .isNoContent()

    }

    @Test
    void shouldDeleteAllRecipes() throws Exception {

        doNothing().when(recipeService).deleteAllRecipe();
        this.mockMvc.perform(delete("/deleteAll"))
                .andExpect(status().isOk());
        //Si j'aurais fait passer HttpStatus.NO_CONTENT a ce moment la j'aurais mis .isNoContent()


    }


    @Test
    void shouldUpdateRecipe() throws Exception {

        when(recipeService.updateRecipe(any(Recipe.class))).thenReturn(recipe);
        this.mockMvc.perform(put("/recipes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipe)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(recipe.getName())));
    }

    @Test
    void shouldAddIngredientToRecipe() throws Exception {
        when(recipeService.addIngredientToRecipe(anyLong(),any(Ingredient.class))).thenReturn(recipe);
        this.mockMvc.perform(put("/recipes/ingredients/add/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipe)))
                .andExpect(status().isOk());

    }



    @Test
    void shouldDeleteIngredientFromRecipe() throws Exception {
        when(recipeService.addIngredientToRecipe(anyLong(),any(Ingredient.class))).thenReturn(recipe);
        this.mockMvc.perform(delete("/recipes/ingredients/{id}/{ingredientId}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipe)))
                .andExpect(status().isOk());

    }




}


