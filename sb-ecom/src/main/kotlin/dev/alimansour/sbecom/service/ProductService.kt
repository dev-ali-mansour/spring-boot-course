package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.payload.ProductDTO
import dev.alimansour.sbecom.payload.ProductResponse
import org.springframework.data.domain.Pageable
import org.springframework.web.multipart.MultipartFile

interface ProductService {
    fun addProduct(categoryId: Long, productDTO: ProductDTO): ProductDTO
    fun getAllProducts(keyword: String, category: String, pageable: Pageable): ProductResponse
    fun searchByCategory(categoryId: Long, pageable: Pageable): ProductResponse
    fun searchByKeyword(keyword: String, pageable: Pageable): ProductResponse
    fun updateProduct(id: Long, productDTO: ProductDTO): ProductDTO
    fun deleteProduct(id: Long): ProductDTO
    fun updateProductImage(id: Long, image: MultipartFile): ProductDTO
}
