package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.model.Category

interface CategoryService {
    fun getCategories(): List<Category>
    fun createCategory(category: Category)
    fun deleteCategory(categoryId: Long): String
    fun updateCategory(category: Category, categoryId: Long): Category
}
