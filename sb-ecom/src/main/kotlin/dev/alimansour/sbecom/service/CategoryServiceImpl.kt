package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.exception.APIException
import dev.alimansour.sbecom.exception.ResourceNotFoundException
import dev.alimansour.sbecom.model.Category
import dev.alimansour.sbecom.payload.CategoryResponse
import dev.alimansour.sbecom.repository.CategoryRepository
import dev.alimansour.sbecom.toCategoryDTO
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(private val categoryRepository: CategoryRepository) : CategoryService {

    override fun getCategories(): CategoryResponse {
        val categories = categoryRepository.findAll().map { it.toCategoryDTO() }
        if (categories.isEmpty()) {
            throw APIException("No category created till now.")
        }
        return CategoryResponse(content = categories)
    }

    override fun createCategory(category: Category) {
        val savedCategory = categoryRepository.findByName(category.name)
        savedCategory?.let {
            throw APIException(message = "Category with name '${category.name}' already exists!!!")
        } ?: categoryRepository.save(category)
    }

    override fun deleteCategory(categoryId: Long): String {
        categoryRepository.findById(categoryId).orElseThrow {
            ResourceNotFoundException("Category", "id", categoryId)
        }

        categoryRepository.deleteById(categoryId)
        return "Category with categoryId $categoryId deleted successfully!"
    }

    override fun updateCategory(category: Category, categoryId: Long): Category {
        categoryRepository.findById(categoryId).orElseThrow {
            ResourceNotFoundException("Category", "id", categoryId)
        }

        category.id = categoryId
        return categoryRepository.save(category)
    }
}
