package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.config.AppConstants
import dev.alimansour.sbecom.exception.ResourceNotFoundException
import dev.alimansour.sbecom.mapper.toDTO
import dev.alimansour.sbecom.mapper.toEntity
import dev.alimansour.sbecom.model.Product
import dev.alimansour.sbecom.payload.ProductDTO
import dev.alimansour.sbecom.payload.ProductResponse
import dev.alimansour.sbecom.repository.CategoryRepository
import dev.alimansour.sbecom.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : ProductService {
    override fun addProduct(
        categoryId: Long,
        productDTO: ProductDTO
    ): ProductDTO {
        val category = categoryRepository.findById(categoryId)
            .orElseThrow { ResourceNotFoundException(resourceName = "Category", field = "id", fieldId = categoryId) }

        val product = productDTO.toEntity().apply {
            this.image = "default.png"
            this.category = category
            this.specialPrice = calculateSpecialPrice()
        }

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

    override fun updateProduct(id: Long, productDTO: ProductDTO): ProductDTO {
        val existedProduct = productRepository.findById(id).orElseThrow {
            ResourceNotFoundException(resourceName = "Product", field = "id", fieldId = id)
        }

        val product = productDTO.toEntity().apply {
            this.id = id
            this.image = existedProduct.image
            this.specialPrice = calculateSpecialPrice()
        }

        val updatedProduct = productRepository.save(product)
        return updatedProduct.toDTO()
    }

    override fun deleteProduct(id: Long): ProductDTO {
        val product = productRepository.findById(id).orElseThrow {
            ResourceNotFoundException(resourceName = "Product", field = "id", fieldId = id)
        }

        productRepository.delete(product)
        return product.toDTO()

    }

    override fun updateProductImage(id: Long, image: MultipartFile): ProductDTO {
        val product = productRepository.findById(id)
            .orElseThrow { ResourceNotFoundException(resourceName = "Product", field = "id", fieldId = id) }


        val fileName = uploadImage(AppConstants.IMAGES_PATH, image)
        product.image = fileName

        val updatedProduct = productRepository.save(product)
        return updatedProduct.toDTO()
    }

    private fun uploadImage(path: String, file: MultipartFile): String {
        val randomId = UUID.randomUUID().toString()
        file.originalFilename?.let { originalFilename ->
            val fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'))
            val fileName = "${randomId}${fileExtension}"
            val filePath = "$path${File.separator}$fileName"

            val folder = File(path)
            if (!folder.exists()) {
                folder.mkdir()
            }
            Files.copy(file.inputStream, Paths.get(filePath))
            return fileName
        }
        throw RuntimeException("Invalid file extension")
    }

    private fun Product.calculateSpecialPrice(): Double =
        price * (1 - discount * 0.01) //price - ((discount * 0.01) * price)
}
