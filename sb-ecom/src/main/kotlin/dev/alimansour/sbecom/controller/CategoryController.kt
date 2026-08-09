package dev.alimansour.sbecom.controller

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
        val savedCategoryDTO = categoryService.createCategory(categoryDTO)
        return ResponseEntity(savedCategoryDTO, HttpStatus.CREATED)
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    fun deleteCategory(@PathVariable categoryId: Long): ResponseEntity<String> {
        val response = categoryService.deleteCategory(categoryId)
        return ResponseEntity(response, HttpStatus.OK)
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
