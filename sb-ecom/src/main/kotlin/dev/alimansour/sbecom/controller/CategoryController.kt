package dev.alimansour.sbecom.controller

import dev.alimansour.sbecom.model.Category
import dev.alimansour.sbecom.service.CategoryService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping("/public/categories")
    fun getCategories(): ResponseEntity<List<Category>> {
        val categories = categoryService.getCategories()
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(categories)
    }

    @PostMapping("/admin/categories")
    fun createCategory(@Valid @RequestBody category: Category): ResponseEntity<String> {
        categoryService.createCategory(category)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("Category added successfully!")
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    fun deleteCategory(@PathVariable categoryId: Long): ResponseEntity<String> {
        val category = categoryService.deleteCategory(categoryId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(category)
    }

    @PutMapping("/admin/categories/{categoryId}")
    fun updateCategory(@Valid @RequestBody category: Category, @PathVariable categoryId: Long): ResponseEntity<String> {
        val savedCategory = categoryService.updateCategory(category, categoryId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Category with category id: ${savedCategory.id} updated successfully!")
    }
}
