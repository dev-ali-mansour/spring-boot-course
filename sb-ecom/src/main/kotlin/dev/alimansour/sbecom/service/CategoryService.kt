package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.payload.CategoryDTO
import dev.alimansour.sbecom.payload.CategoryResponse

interface CategoryService {
    fun getCategories(page: Int, size: Int): CategoryResponse
    fun createCategory(categoryDTO: CategoryDTO): CategoryDTO
    fun deleteCategory(categoryId: Long): CategoryDTO
    fun updateCategory(categoryId: Long, categoryDTO: CategoryDTO): CategoryDTO
}
