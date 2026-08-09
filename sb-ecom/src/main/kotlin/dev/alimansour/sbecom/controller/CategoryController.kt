package dev.alimansour.sbecom.controller

import dev.alimansour.sbecom.model.Category
import dev.alimansour.sbecom.payload.CategoryDTO
import dev.alimansour.sbecom.payload.CategoryResponse
import dev.alimansour.sbecom.service.CategoryService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping("/public/categories")
    fun getCategories(): ResponseEntity<CategoryResponse> {
        val response = categoryService.getCategories()
        return ResponseEntity(response, HttpStatus.OK)
    }

    @PostMapping("/admin/categories")
    fun createCategory(@Valid @RequestBody categoryDTO: CategoryDTO): ResponseEntity<CategoryDTO> {
        val savedCategory = categoryService.createCategory(categoryDTO)
        return ResponseEntity(savedCategory, HttpStatus.CREATED)
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    fun deleteCategory(@PathVariable categoryId: Long): ResponseEntity<String> {
        val category = categoryService.deleteCategory(categoryId)
        return ResponseEntity(category, HttpStatus.OK)
    }

    @PutMapping("/admin/categories/{categoryId}")
    fun updateCategory(@Valid @RequestBody category: Category, @PathVariable categoryId: Long): ResponseEntity<String> {
        val savedCategory = categoryService.updateCategory(category, categoryId)
        return ResponseEntity(
            "Category with category id: ${savedCategory.id} updated successfully!",
            HttpStatus.OK
        )
    }
}
