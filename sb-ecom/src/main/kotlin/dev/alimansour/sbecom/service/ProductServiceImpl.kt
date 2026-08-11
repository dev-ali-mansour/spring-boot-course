package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.exception.ResourceNotFoundException
import dev.alimansour.sbecom.mapper.toDTO
import dev.alimansour.sbecom.model.Product
import dev.alimansour.sbecom.payload.ProductDTO
import dev.alimansour.sbecom.payload.ProductResponse
import dev.alimansour.sbecom.repository.CategoryRepository
import dev.alimansour.sbecom.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : ProductService {
    override fun addProduct(
        categoryId: Long,
        product: Product
    ): ProductDTO {
        val category = categoryRepository.findById(categoryId)
            .orElseThrow { ResourceNotFoundException(resourceName = "Category", field = "id", fieldId = categoryId) }

        product.image = "default.png"
        product.category = category
        val specialPrice = product.price - ((product.discount * 0.01) * product.price)
        product.specialPrice = specialPrice
        val savedProduct = productRepository.save(product)
        return savedProduct.toDTO()
    }

    override fun getAllProducts(): ProductResponse {
        val products = productRepository.findAll()
            .map { it.toDTO() }
        return ProductResponse(content = products)
    }

    override fun searchByCategory(categoryId: Long): ProductResponse {
        val category = categoryRepository.findById(categoryId)
            .orElseThrow { ResourceNotFoundException(resourceName = "Category", field = "id", fieldId = categoryId) }

        val products = productRepository.findByCategoryOrderByPriceAsc(category)
            .map { it.toDTO() }
        return ProductResponse(content = products)
    }

    override fun searchByKeyword(keyword: String): ProductResponse {
        val products = productRepository.findByNameLikeIgnoreCase("%$keyword%")
            .map { it.toDTO() }
        return ProductResponse(content = products)
    }
}
