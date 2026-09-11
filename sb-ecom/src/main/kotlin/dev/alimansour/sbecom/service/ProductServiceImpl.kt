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
import dev.alimansour.sbecom.util.AuthUtil
import dev.alimansour.sbecom.util.roundToTwoDecimals
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ProductServiceImpl(
    @Value($$"${project.images.path}") private val path: String,
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val cartRepository: CartRepository,
    private val cartService: CartService,
    private val fileService: FileService,
    private val authUtil: AuthUtil,
) : ProductService {
    override fun addProduct(
        categoryId: Long, productDTO: ProductDTO
    ): ProductDTO {
        val currentUser = authUtil.loggedInUser()

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
            this.user = currentUser
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
                    criteriaBuilder.lower(root.get("name")), "%${keyword.lowercase()}%"
                )
            }
        }

        if (category.isNotEmpty()) {
            spec = spec.and { root, _, criteriaBuilder ->
                criteriaBuilder.like(root.get<Category>("category").get("name"), category)
            }
        }

        val page = productRepository.findAll(spec, pageable)
        val products = page.content.map {
            it.toDTO().copy(image = fileService.constructImageUrl(it.image))
        }

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
        val products = page.content.map {
            it.toDTO().copy(image = fileService.constructImageUrl(it.image))
        }

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
        val products = page.content.map {
            it.toDTO().copy(image = fileService.constructImageUrl(it.image))
        }

        return ProductResponse(
            content = products,
            pageNumber = page.number,
            pageSize = page.size,
            totalPages = page.totalPages,
            totalElements = page.totalElements,
            lastPage = page.isLast
        )
    }

    override fun updateProduct(id: Long, productDTO: ProductDTO, forSeller: Boolean): ProductDTO {
        val existedProduct = productRepository.findById(id).orElseThrow {
            ResourceNotFoundException(resourceName = "Product", field = "id", fieldId = id)
        }

        if (forSeller) {
            val currentUser = authUtil.loggedInUser()
            if (existedProduct.user?.id != currentUser.id) {
                throw APIException("You are not authorized to update this product.")
            }
        }

        val product = productDTO.toEntity().apply {
            this.id = id
            this.image = existedProduct.image
            this.category = existedProduct.category
            this.user = existedProduct.user
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

    override fun deleteProduct(id: Long, forSeller: Boolean): ProductDTO {
        val product = productRepository.findById(id).orElseThrow {
            ResourceNotFoundException(resourceName = "Product", field = "id", fieldId = id)
        }

        if (forSeller) {
            val currentUser = authUtil.loggedInUser()
            if (product.user?.id != currentUser.id) {
                throw APIException("You are not authorized to delete this product.")
            }
        }

        cartService.deleteProductFromAllCarts(productId = id)

        productRepository.delete(product)
        return product.toDTO()
    }

    override fun updateProductImage(id: Long, image: MultipartFile, forSeller: Boolean): ProductDTO {
        val product = productRepository.findById(id)
            .orElseThrow { ResourceNotFoundException(resourceName = "Product", field = "id", fieldId = id) }

        if (forSeller) {
            val currentUser = authUtil.loggedInUser()
            if (product.user?.id != currentUser.id) {
                throw APIException("You are not authorized to update this product's image.")
            }
        }

        val fileName = fileService.uploadFile(path, image)
        product.image = fileName

        val updatedProduct = productRepository.save(product)
        return updatedProduct.toDTO()
    }

    override fun getAllProductsForAdmin(pageable: Pageable): ProductResponse {
        val page = productRepository.findAll(pageable)
        val products = page.content.map {
            it.toDTO().copy(image = fileService.constructImageUrl(it.image))
        }

        return ProductResponse(
            content = products,
            pageNumber = page.number,
            pageSize = page.size,
            totalPages = page.totalPages,
            totalElements = page.totalElements,
            lastPage = page.isLast
        )
    }

    override fun getAllProductsForSeller(pageable: Pageable): ProductResponse {
        val user = authUtil.loggedInUser();
        val page = productRepository.findByUser(user, pageable)
        val products = page.content.map {
            it.toDTO().copy(image = fileService.constructImageUrl(it.image))
        }

        return ProductResponse(
            content = products,
            pageNumber = page.number,
            pageSize = page.size,
            totalPages = page.totalPages,
            totalElements = page.totalElements,
            lastPage = page.isLast
        )
    }

    private fun Product.calculateSpecialPrice(): Double =
        (price * (1 - discount * 0.01)).roundToTwoDecimals() //price - ((discount * 0.01) * price)
}
