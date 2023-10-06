package com.vlad.htmxrecipes.repository;

import com.vlad.htmxrecipes.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Integer> {

}