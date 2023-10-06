package com.vlad.htmxrecipes.controller;

import com.vlad.htmxrecipes.model.Recipe;
import com.vlad.htmxrecipes.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RecipeController.class)
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeRepository recipeRepository;

    @Test
    public void testGetRecipes() throws  Exception {
        when(recipeRepository.findAll())
                .thenReturn(List.of(new Recipe(1, "My Test Recipe", "test")));

        mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("My Test Recipe")));
    }

    @Test
    public void testDeleteRecipe() throws  Exception {
        doNothing().when(recipeRepository).deleteById(1);

        mockMvc.perform(delete("/recipes/{id}", 1))
                .andExpect(status().isAccepted())
                .andExpect(content().string(""));
    }

    @Test
    public void testCreateRecipe() throws  Exception {
        Recipe recipe = new Recipe(1, "new name", "new description");
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(recipeRepository.findAll()).thenReturn(List.of(recipe));

        mockMvc.perform(post("/recipes")
                        .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                        .param("name", recipe.name())
                        .param("description", recipe.description()))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(recipe.name())));
    }

    @Test
    public void testCreateRecipe_missingName() throws  Exception {
        Recipe recipe = new Recipe(1, "new name", "new description");
        when(recipeRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        mockMvc.perform(post("/recipes")
                        .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                        .param("description", recipe.description()))
                .andExpect(status().isCreated()) //TODO change response code
                .andExpect(content().string(not(containsString(recipe.name()))));
    }

}