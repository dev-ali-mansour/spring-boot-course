package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.exception.APIException
import dev.alimansour.sbecom.exception.ResourceNotFoundException
import dev.alimansour.sbecom.mapper.toDTO
import dev.alimansour.sbecom.mapper.toEntity
import dev.alimansour.sbecom.model.Cart
import dev.alimansour.sbecom.model.Category
import dev.alimansour.sbecom.model.Product
import dev.alimansour.sbecom.payload.ProductDTO
import dev.alimansour.sbecom.payload.ProductResponse
import dev.alimansour.sbecom.repository.CartRepository
import dev.alimansour.sbecom.repository.CategoryRepository
import dev.alimansour.sbecom.repository.ProductRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ProductServiceImpl(
    @Value($$"${project.images.path}") private val path: String,
    @Value($$"${image.base.url}") private val imageBaseUrl: String,
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val cartRepository: CartRepository,
    private val cartService: CartService,
    private val fileService: FileService,
) : ProductService {
    override fun addProduct(
        categoryId: Long, productDTO: ProductDTO
    ): ProductDTO {
        val category = categoryRepository.findById(categoryId).orElseThrow {
            ResourceNotFoundException(resourceName = "Category", field = "id", fieldId = categoryId)
        }

        category.products.forEach { product ->
            if (product.name.equals(productDTO.name, ignoreCase = true)) {
                throw APIException("Product `${productDTO.name}` already exists!")
            }
        }

        val product = productDTO.toEntity().apply {
            this.image = "default.png"
            this.category = category
            this.specialPrice = calculateSpecialPrice()
        }

        val savedProduct = productRepository.save(product)
        return savedProduct.toDTO()
    }

    override fun getAllProducts(keyword: String, category: String, pageable: Pageable): ProductResponse {
        var spec: Specification<Product> = Specification.unrestricted()
        if (keyword.isNotEmpty()) {
            spec = spec.and { root, _, criteriaBuilder ->
                criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%${keyword.lowercase()}%"
                )
            }
        }

        if (category.isNotEmpty()) {
            spec = spec.and { root, _, criteriaBuilder ->
                criteriaBuilder.like(root.get<Category>("category").get("name"), category)
            }
        }

        val page = productRepository.findAll(spec, pageable)
        val products = page.content.map { it.toDTO().copy(image = constructImageUrl(it.image)) }

        return ProductResponse(
            content = products,
            pageNumber = page.number,
            pageSize = page.size,
            totalPages = page.totalPages,
            totalElements = page.totalElements,
            lastPage = page.isLast
        )
    }

    override fun searchByCategory(categoryId: Long, pageable: Pageable): ProductResponse {
        val category = categoryRepository.findById(categoryId)
            .orElseThrow { ResourceNotFoundException(resourceName = "Category", field = "id", fieldId = categoryId) }

        val page = productRepository.findByCategory(category, pageable)
        val products = page.content.map { it.toDTO().copy(image = constructImageUrl(it.image)) }

        return ProductResponse(
            content = products,
            pageNumber = page.number,
            pageSize = page.size,
            totalPages = page.totalPages,
            totalElements = page.totalElements,
            lastPage = page.isLast
        )
    }

    override fun searchByKeyword(keyword: String, pageable: Pageable): ProductResponse {
        val page = productRepository.findByNameLikeIgnoreCase("%$keyword%", pageable)
        val products = page.content.map { it.toDTO().copy(image = constructImageUrl(it.image)) }

        return ProductResponse(
            content = products,
            pageNumber = page.number,
            pageSize = page.size,
            totalPages = page.totalPages,
            totalElements = page.totalElements,
            lastPage = page.isLast
        )
    }

    override fun updateProduct(id: Long, productDTO: ProductDTO): ProductDTO {
        val existedProduct = productRepository.findById(id).orElseThrow {
            ResourceNotFoundException(resourceName = "Product", field = "id", fieldId = id)
        }

        val product = productDTO.toEntity().apply {
            this.id = id
            this.image = existedProduct.image
            this.category = existedProduct.category
            this.specialPrice = calculateSpecialPrice()
        }

        val updatedProduct = productRepository.save(product)
        val carts: List<Cart> = cartRepository.findCartByProductId(
            requireNotNull(product.id) { "Product ID must not be null" })
        val cartDTOs = carts.map { it.toDTO() }

        cartDTOs.forEach { cart ->
            cartService.updateProductInCarts(
                cartId = requireNotNull(cart.id) { "Cart ID must not be null" }, productId = id
            )
        }

        return updatedProduct.toDTO()
    }

    override fun deleteProduct(id: Long): ProductDTO {
        val product = productRepository.findById(id).orElseThrow {
            ResourceNotFoundException(resourceName = "Product", field = "id", fieldId = id)
        }

        cartService.deleteProductFromAllCarts(productId = id)

        productRepository.delete(product)
        return product.toDTO()
    }

    override fun updateProductImage(id: Long, image: MultipartFile): ProductDTO {
        val product = productRepository.findById(id)
            .orElseThrow { ResourceNotFoundException(resourceName = "Product", field = "id", fieldId = id) }


        val fileName = fileService.uploadFile(path, image)
        product.image = fileName

        val updatedProduct = productRepository.save(product)
        return updatedProduct.toDTO()
    }


    private fun Product.calculateSpecialPrice(): Double =
        price * (1 - discount * 0.01) //price - ((discount * 0.01) * price)

    private fun constructImageUrl(imageName: String): String =
        if (imageBaseUrl.endsWith("/")) "$imageBaseUrl$imageName"
        else "$imageBaseUrl/$imageName"
}
