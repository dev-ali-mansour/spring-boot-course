package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.model.Category
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class CategoryServiceImpl : CategoryService {
    private val categories: MutableList<Category> = mutableListOf()
    private var nextId: Long = 1

    override fun getCategories(): List<Category> = categories

    override fun createCategory(category: Category) {
        categories.add(category.copy(id = nextId++))
    }

    override fun deleteCategory(categoryId: Long): String {
        return categories.firstOrNull { it.id == categoryId }?.let { category ->
            categories.remove(category)
            "Category with categoryId $categoryId deleted successfully!"
        } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found!")
    }
}
