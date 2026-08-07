package dev.alimansour.sbecom.controller

import dev.alimansour.sbecom.model.Category
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CategoryController {
    private val categories: List<Category> = mutableListOf()

    @GetMapping("/api/public/categories")
    fun getCategories(): List<Category> = categories
}
