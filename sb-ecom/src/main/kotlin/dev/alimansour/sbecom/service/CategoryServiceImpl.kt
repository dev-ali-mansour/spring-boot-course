package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.model.Category
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class CategoryServiceImpl : CategoryService {
    private val categories: MutableList<Category> = Collections.synchronizedList(mutableListOf())
    private var nextId: Long = 1

    override fun getCategories(): List<Category> = categories.toList()

    override fun createCategory(category: Category) {
        category.id = nextId++
        categories.add(category)
    }

    override fun deleteCategory(categoryId: Long): String {
        val category = categories.firstOrNull { it.id == categoryId }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found!")

        categories.remove(category)
        return "Category with categoryId $categoryId deleted successfully!"
    }

    override fun updateCategory(category: Category, categoryId: Long): Category {
        val existingCategory = categories.firstOrNull { it.id == categoryId }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found!")

        val index = categories.indexOf(existingCategory)
        category.id = categoryId
        categories[index] = category
        return category
    }
}

