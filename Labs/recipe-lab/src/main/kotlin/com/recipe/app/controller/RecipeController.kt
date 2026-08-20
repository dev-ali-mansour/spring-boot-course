package com.recipe.app.controller

import com.recipe.app.model.Recipe
import com.recipe.app.repository.RecipeRepository
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.jvm.optionals.getOrElse

@RestController
@RequestMapping("/api/recipes")
class RecipeController(private val recipeRepository: RecipeRepository) {

    @PostMapping
    fun addRecipe(@Valid @RequestBody recipe: Recipe): ResponseEntity<Recipe> {
        val savedRecipe = recipeRepository.save(recipe)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecipe)
    }

    @GetMapping
    fun getAllRecipes(
        @RequestParam(required = false) category: String?,
        @RequestParam(required = false) rating: Int?
    ): ResponseEntity<List<Recipe>> {
        val recipes = when {
            !category.isNullOrBlank() && rating != null -> {
                recipeRepository.findByCategoryAndRating(category, rating)
            }

            !category.isNullOrBlank() -> {
                recipeRepository.findByCategory(category)
            }

            rating != null -> {
                recipeRepository.findByRating(rating)
            }

            else -> {
                recipeRepository.findAll()
            }
        }
        return ResponseEntity.ok(recipes)
    }

    @GetMapping("/{id}")
    fun getRecipeById(@PathVariable id: Long): ResponseEntity<Recipe> =
        recipeRepository.findById(id)
            .map { ResponseEntity.ok(it) }
            .getOrElse { ResponseEntity.notFound().build() }

}