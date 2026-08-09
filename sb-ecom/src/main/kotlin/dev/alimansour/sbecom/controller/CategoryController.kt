package dev.alimansour.sbecom.controller

import dev.alimansour.sbecom.config.AppConstants
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
    fun getCategories(
        @RequestParam(value = "page", defaultValue = AppConstants.PAGE_NUMBER, required = false) page: Int,
        @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE, required = false) size: Int,
        @RequestParam(value = "sort", defaultValue = AppConstants.SORT_CATEGORIES, required = false) sort: String,
    ): ResponseEntity<CategoryResponse> {
        val response = categoryService.getCategories(page, size, sort)
        return ResponseEntity(response, HttpStatus.OK)
    }

    @PostMapping("/admin/categories")
    fun createCategory(@Valid @RequestBody categoryDTO: CategoryDTO): ResponseEntity<CategoryDTO> {
        val savedCategoryDTO = categoryService.createCategory(categoryDTO)
        return ResponseEntity(savedCategoryDTO, HttpStatus.CREATED)
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    fun deleteCategory(@PathVariable categoryId: Long): ResponseEntity<CategoryDTO> {
        val deletedCategory = categoryService.deleteCategory(categoryId)
        return ResponseEntity(deletedCategory, HttpStatus.OK)
    }

    @PutMapping("/admin/categories/{categoryId}")
    fun updateCategory(
        @PathVariable categoryId: Long,
        @Valid @RequestBody categoryDTO: CategoryDTO
    ): ResponseEntity<CategoryDTO> {
        if (categoryDTO.id != null && categoryDTO.id != categoryId) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        val savedCategoryDTO = categoryService.updateCategory(categoryId, categoryDTO)
        return ResponseEntity(savedCategoryDTO, HttpStatus.OK)
    }
}
