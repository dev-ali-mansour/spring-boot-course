package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.model.Category
import dev.alimansour.sbecom.payload.CategoryDTO
import dev.alimansour.sbecom.payload.CategoryResponse

interface CategoryService {
    fun getCategories(): CategoryResponse
    fun createCategory(categoryDTO: CategoryDTO): CategoryDTO
    fun deleteCategory(categoryId: Long): String
    fun updateCategory(category: Category, categoryId: Long): Category
}
