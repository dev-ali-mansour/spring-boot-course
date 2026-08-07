package dev.alimansour.sbecom.controller

import dev.alimansour.sbecom.model.Category
import dev.alimansour.sbecom.service.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping("/api/public/categories")
    fun getCategories(): ResponseEntity<List<Category>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(categoryService.getCategories())

    @PostMapping("/api/public/categories")
    fun createCategory(@RequestBody category: Category): ResponseEntity<String> {
        categoryService.createCategory(category)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("Category added successfully!")
    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    fun deleteCategory(@PathVariable categoryId: Long): ResponseEntity<String> =
        runCatching {
//            ResponseEntity(categoryService.deleteCategory(categoryId), HttpStatus.OK)
//            ResponseEntity.ok(categoryService.deleteCategory(categoryId))
            ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.deleteCategory(categoryId))
        }.getOrElse { t ->
            if (t is ResponseStatusException) {
                ResponseEntity
                    .status(t.statusCode)
                    .body(t.reason)

            } else ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(t.message)
        }

    @PutMapping("/api/admin/categories/{categoryId}")
    fun updateCategory(@RequestBody category: Category, @PathVariable categoryId: Long): ResponseEntity<String> =
        runCatching {
            val savedCategory = categoryService.updateCategory(category,categoryId)
            ResponseEntity
                .status(HttpStatus.OK)
                .body("Category with category id: ${savedCategory.id} updated successfully!")
        }.getOrElse { t ->
            if (t is ResponseStatusException) {
                ResponseEntity
                    .status(t.statusCode)
                    .body(t.reason)

            } else ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(t.message)
        }
}
