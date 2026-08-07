package dev.alimansour.sbecom.controller

import dev.alimansour.sbecom.model.Category
import dev.alimansour.sbecom.service.CategoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CategoryController(private val categoryService: CategoryService, service: CategoryService) {

    @GetMapping("/api/public/categories")
    fun getCategories(): List<Category> = categoryService.getCategories()

    @PostMapping("/api/public/categories")
    fun createCategory(@RequestBody category: Category): String {
        categoryService.createCategory(category)
        return "Category added successfully!"
    }
}
