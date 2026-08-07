package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.model.Category
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl : CategoryService {
    private val categories: MutableList<Category> = mutableListOf()
    private var nextId: Long = 1

    override fun getCategories(): List<Category> = categories

    override fun createCategory(category: Category) {
        categories.add(category.copy(id = nextId++))
    }
}
