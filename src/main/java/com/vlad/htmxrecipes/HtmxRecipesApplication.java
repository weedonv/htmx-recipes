package com.vlad.htmxrecipes;

import com.vlad.htmxrecipes.model.Recipe;
import com.vlad.htmxrecipes.repository.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HtmxRecipesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HtmxRecipesApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RecipeRepository recipeRepository) {
		return args -> {
			recipeRepository.save(new Recipe(null, "Pancakes", "delicious and quick"));
			recipeRepository.save(new Recipe(null, "Chicken Burgers", "buttery and herby"));
			recipeRepository.save(new Recipe(null, "Soup", "beef and vegetables"));
			recipeRepository.save(new Recipe(null, "Sausage and Mash", "just add gravy"));
			recipeRepository.save(new Recipe(null, "Feta Salad", "refreshing and crisp"));
		};
	}
}
