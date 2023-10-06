package com.vlad.htmxrecipes.controller;

import com.vlad.htmxrecipes.model.Recipe;
import com.vlad.htmxrecipes.repository.RecipeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeRepository recipeRepository;

    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping
    public String getRecipes(Model model) {
        model.addAttribute("recipes", recipeRepository.findAll());
        return "/pages/recipes";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(value = "/{id}",produces = MediaType.TEXT_HTML_VALUE)
    public String deleteRecipe(@PathVariable Integer id) {
        recipeRepository.deleteById(id);
        return "";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_HTML_VALUE)
    public String createRecipe(@RequestParam Map<String, String> body, Model model) {

        if (!body.getOrDefault("name", "").isBlank() &&
                !body.getOrDefault("description", "").isBlank()) {

            recipeRepository.save(new Recipe(null, body.get("name"), body.get("description")));
        }

        model.addAttribute("recipes", recipeRepository.findAll());

        return "/pages/recipes :: recipes-list";
    }
}
