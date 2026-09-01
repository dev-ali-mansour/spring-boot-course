package dev.alimansour.sbecom.controller

import dev.alimansour.sbecom.config.AppConstants
import dev.alimansour.sbecom.exception.APIException
import dev.alimansour.sbecom.payload.CategoryDTO
import dev.alimansour.sbecom.payload.CategoryResponse
import dev.alimansour.sbecom.service.CategoryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
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

    @Tag(name = "Category APIs", description = "APIs for managing categories")
    @GetMapping("/public/categories")
    fun getCategories(
        @PageableDefault(
            page = AppConstants.PAGE_NUMBER,
            size = AppConstants.PAGE_SIZE,
            sort = [AppConstants.SORT_CATEGORIES_BY],
            direction = Sort.Direction.ASC
        ) pageable: Pageable,
    ): ResponseEntity<CategoryResponse> {
        val response = categoryService.getCategories(pageable)
        return ResponseEntity(response, HttpStatus.OK)
    }

    @Tag(name = "Category APIs", description = "APIs for managing categories")
    @Operation(
        summary = "Create a new category",
        description = "API for creating a new category"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Category is created successfully"),
            ApiResponse(responseCode = "400", description = "Invalid input", content = [Content()]),
            ApiResponse(responseCode = "500", description = "Internal Server Error", content = [Content()]),
        ]
    )
    @PostMapping("/admin/categories")
    fun createCategory(@Valid @RequestBody categoryDTO: CategoryDTO): ResponseEntity<CategoryDTO> {
        val savedCategoryDTO = categoryService.createCategory(categoryDTO)
        return ResponseEntity(savedCategoryDTO, HttpStatus.CREATED)
    }

    @Tag(name = "Category APIs", description = "APIs for managing categories")
    @DeleteMapping("/admin/categories/{categoryId}")
    fun deleteCategory(
        @Parameter(description = "ID of the category that you wish to delete")
        @PathVariable categoryId: Long
    ): ResponseEntity<CategoryDTO> {
        val deletedCategory = categoryService.deleteCategory(categoryId)
        return ResponseEntity(deletedCategory, HttpStatus.OK)
    }

    @Tag(name = "Category APIs", description = "APIs for managing categories")
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
