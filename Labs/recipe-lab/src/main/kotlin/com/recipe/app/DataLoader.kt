package com.recipe.app

import com.recipe.app.model.Recipe
import com.recipe.app.repository.RecipeRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataLoader(private val recipeRepository: RecipeRepository) : CommandLineRunner {

    override fun run(vararg args: String) {
        recipeRepository.save(Recipe("Spaghetti Carbonara", "pasta", 5))
        recipeRepository.save(Recipe("Caesar Salad", "salad", 4))
        recipeRepository.save(Recipe("Chicken Tikka Masala", "curry", 5))
        recipeRepository.save(Recipe("Greek Salad", "salad", 3))
        recipeRepository.save(Recipe("Penne Arrabiata", "pasta", 4))
    }
}
