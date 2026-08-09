package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.exception.APIException
import dev.alimansour.sbecom.exception.ResourceNotFoundException
import dev.alimansour.sbecom.mapper.toCategory
import dev.alimansour.sbecom.mapper.toCategoryDTO
import dev.alimansour.sbecom.payload.CategoryDTO
import dev.alimansour.sbecom.payload.CategoryResponse
import dev.alimansour.sbecom.repository.CategoryRepository
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

    override fun createCategory(categoryDTO: CategoryDTO): CategoryDTO {
        categoryRepository.findByName(categoryDTO.name)?.let {
            throw APIException(message = "Category with name '${categoryDTO.name}' already exists!!!")
        }
        val category = categoryDTO.toCategory()
        val savedCategory = categoryRepository.save(category)
        return savedCategory.toCategoryDTO()
    }

    override fun deleteCategory(categoryId: Long): String {
        categoryRepository.findById(categoryId).orElseThrow {
            ResourceNotFoundException("Category", "id", categoryId)
        }

        categoryRepository.deleteById(categoryId)
        return "Category with categoryId $categoryId deleted successfully!"
    }

    override fun updateCategory(categoryId: Long, categoryDTO: CategoryDTO): CategoryDTO {
        categoryRepository.findById(categoryId).orElseThrow {
            ResourceNotFoundException("Category", "id", categoryId)
        }
        val category = categoryDTO.toCategory()
        category.id = categoryId
        val savedCategory = categoryRepository.save(category)
        return savedCategory.toCategoryDTO()
    }
}
