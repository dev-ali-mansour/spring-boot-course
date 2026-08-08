package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.model.Category
import dev.alimansour.sbecom.repository.CategoryRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class CategoryServiceImpl(private val categoryRepository: CategoryRepository) : CategoryService {

    override fun getCategories(): List<Category> = categoryRepository.findAll()

    override fun createCategory(category: Category) {
        categoryRepository.save(category)
    }

    override fun deleteCategory(categoryId: Long): String {
        categoryRepository.findById(categoryId)
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found!")
            }

        categoryRepository.deleteById(categoryId)
        return "Category with categoryId $categoryId deleted successfully!"
    }

    override fun updateCategory(category: Category, categoryId: Long): Category {
        categoryRepository.findById(categoryId)
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found!")
            }

        category.id = categoryId
        return categoryRepository.save(category)
    }
}
