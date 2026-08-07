package dev.alimansour.sbecom.controller

import dev.alimansour.sbecom.model.Category
import dev.alimansour.sbecom.service.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
class CategoryController(private val categoryService: CategoryService, service: CategoryService) {

    @GetMapping("/api/public/categories")
    fun getCategories(): List<Category> = categoryService.getCategories()

    @PostMapping("/api/public/categories")
    fun createCategory(@RequestBody category: Category): String {
        categoryService.createCategory(category)
        return "Category added successfully!"
    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    fun deleteCategory(@PathVariable categoryId: Long): ResponseEntity<String> {
        return runCatching {
            ResponseEntity(categoryService.deleteCategory(categoryId), HttpStatus.OK)
        }.getOrElse { t ->
            if (t is ResponseStatusException) {
                ResponseEntity(t.reason, t.statusCode)
            } else ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
