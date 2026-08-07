package dev.alimansour.sbecom.controller

import dev.alimansour.sbecom.model.Category
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CategoryController {
    private val categories: MutableList<Category> = mutableListOf()

    @GetMapping("/api/public/categories")
    fun getCategories(): List<Category> = categories

    @PostMapping("/api/public/categories")
    fun createCategory(@RequestBody category: Category): String {
        categories.add(category)
        return "Category added successfully!"
    }
}
