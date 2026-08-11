package dev.alimansour.sbecom.controller

import dev.alimansour.sbecom.config.AppConstants
import dev.alimansour.sbecom.exception.APIException
import dev.alimansour.sbecom.payload.CategoryDTO
import dev.alimansour.sbecom.payload.CategoryResponse
import dev.alimansour.sbecom.service.CategoryService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping("/public/categories")
    fun getCategories(
        @PageableDefault(
            page = AppConstants.PAGE_NUMBER,
            size = AppConstants.PAGE_SIZE,
            sort = [AppConstants.SORT_BY],
            direction = Sort.Direction.ASC
        ) pageable: Pageable,
    ): ResponseEntity<CategoryResponse> {
        val response = categoryService.getCategories(pageable)
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
        @PathVariable categoryId: Long, @Valid @RequestBody categoryDTO: CategoryDTO
    ): ResponseEntity<CategoryDTO> {
        if (categoryDTO.id != null && categoryDTO.id != categoryId) {
            throw APIException(message = "Resource ID mismatch: URL path specifies id $categoryId, but the request body contains ${categoryDTO.id}")
        }
        val savedCategoryDTO = categoryService.updateCategory(categoryId, categoryDTO)
        return ResponseEntity(savedCategoryDTO, HttpStatus.OK)
    }
}
