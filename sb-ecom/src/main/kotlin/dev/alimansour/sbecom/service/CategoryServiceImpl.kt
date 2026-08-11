package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.exception.APIException
import dev.alimansour.sbecom.exception.ResourceNotFoundException
import dev.alimansour.sbecom.mapper.toDTO
import dev.alimansour.sbecom.mapper.toEntity
import dev.alimansour.sbecom.payload.CategoryDTO
import dev.alimansour.sbecom.payload.CategoryResponse
import dev.alimansour.sbecom.repository.CategoryRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(private val categoryRepository: CategoryRepository) : CategoryService {

    override fun getCategories(page: Int, size: Int, sort: String): CategoryResponse {
        val parts = sort.split(",")
        val sortField = parts[0]
        val sortOrder = when {
            parts.size > 1 && parts[1].equals("desc", ignoreCase = true) -> {
                Sort.by(sortField).descending()
            }

            else -> {
                Sort.by(sortField).ascending()
            }
        }
        val pageDetails = PageRequest.of(page, size, sortOrder)
        val page = categoryRepository.findAll(pageDetails)
        val categories = page.content.map { it.toDTO() }
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

        categoryRepository.deleteById(categoryId)
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
