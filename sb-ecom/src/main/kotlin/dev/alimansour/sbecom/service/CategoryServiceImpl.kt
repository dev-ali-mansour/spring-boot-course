package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.exception.APIException
import dev.alimansour.sbecom.exception.ResourceNotFoundException
import dev.alimansour.sbecom.mapper.toDTO
import dev.alimansour.sbecom.mapper.toEntity
import dev.alimansour.sbecom.payload.CategoryDTO
import dev.alimansour.sbecom.payload.CategoryResponse
import dev.alimansour.sbecom.repository.CategoryRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(private val categoryRepository: CategoryRepository) : CategoryService {

    override fun getCategories(pageable: Pageable): CategoryResponse {
        val page = categoryRepository.findAll(pageable)
        val categories = page.content.map { it.toDTO() }

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
            throw APIException(message = "Category `${categoryDTO.name}` already exists!")
        }
        val category = categoryDTO.toEntity()
        val savedCategory = categoryRepository.save(category)
        return savedCategory.toDTO()
    }

    override fun deleteCategory(categoryId: Long): CategoryDTO {
        val category = categoryRepository.findById(categoryId).orElseThrow {
            ResourceNotFoundException("Category", "id", categoryId)
        }

        categoryRepository.delete(category)
        return category.toDTO()
    }

    override fun updateCategory(categoryId: Long, categoryDTO: CategoryDTO): CategoryDTO {
        categoryRepository.findById(categoryId).orElseThrow {
            ResourceNotFoundException("Category", "id", categoryId)
        }
        val category = categoryDTO.toEntity()
        category.id = categoryId
        val savedCategory = categoryRepository.save(category)
        return savedCategory.toDTO()
    }
}
