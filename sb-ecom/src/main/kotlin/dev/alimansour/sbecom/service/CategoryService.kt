package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.model.Category

interface CategoryService {
    fun getCategories(): List<Category>
    fun createCategory(category: Category)
}
