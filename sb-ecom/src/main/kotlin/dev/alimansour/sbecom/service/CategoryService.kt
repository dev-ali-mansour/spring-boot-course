package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.payload.CategoryDTO
import dev.alimansour.sbecom.payload.CategoryResponse
import org.springframework.data.domain.Pageable

interface CategoryService {
    fun getCategories(pageable: Pageable): CategoryResponse
    fun createCategory(categoryDTO: CategoryDTO): CategoryDTO
    fun deleteCategory(categoryId: Long): CategoryDTO
    fun updateCategory(categoryId: Long, categoryDTO: CategoryDTO): CategoryDTO
}
