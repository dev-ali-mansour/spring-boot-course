package com.recipe.app.repository

import com.recipe.app.model.Recipe
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecipeRepository : JpaRepository<Recipe, Long> {
    fun findByCategory(category: String): List<Recipe>
    fun findByRating(rating: Int): List<Recipe>
    fun findByCategoryAndRating(category: String, rating: Int): List<Recipe>
}
