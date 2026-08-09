package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.exception.APIException
import dev.alimansour.sbecom.exception.ResourceNotFoundException
import dev.alimansour.sbecom.mapper.toCategory
import dev.alimansour.sbecom.mapper.toCategoryDTO
import dev.alimansour.sbecom.payload.CategoryDTO
import dev.alimansour.sbecom.payload.CategoryResponse
import dev.alimansour.sbecom.repository.CategoryRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(private val categoryRepository: CategoryRepository) : CategoryService {

    override fun getCategories(page: Int, size: Int): CategoryResponse {
        val pageDetails = PageRequest.of(page, size)
        val page = categoryRepository.findAll(pageDetails)
        val categories = page.content.map { it.toCategoryDTO() }
        if (categories.isEmpty()) {
            throw APIException("No category created till now.")
        }
        return CategoryResponse(
            content = categories,
            pageNumber = page.number,
            pageSize = page.size,
            totalPages = page.totalPages,
            totalElements = page.totalElements,
            lastPage = page.isLast
        )
    }

    override fun createCategory(categoryDTO: CategoryDTO): CategoryDTO {
        categoryRepository.findByName(categoryDTO.name)?.let {
            throw APIException(message = "Category with name '${categoryDTO.name}' already exists!!!")
        }
        val category = categoryDTO.toCategory()
        val savedCategory = categoryRepository.save(category)
        return savedCategory.toCategoryDTO()
    }

    override fun deleteCategory(categoryId: Long): CategoryDTO {
        val category = categoryRepository.findById(categoryId).orElseThrow {
            ResourceNotFoundException("Category", "id", categoryId)
        }

        categoryRepository.deleteById(categoryId)
        return category.toCategoryDTO()
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
