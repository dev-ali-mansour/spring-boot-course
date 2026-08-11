package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.payload.ProductDTO
import dev.alimansour.sbecom.payload.ProductResponse

interface ProductService {
    fun addProduct(categoryId: Long, productDTO: ProductDTO): ProductDTO
    fun getAllProducts(): ProductResponse
    fun searchByCategory(categoryId: Long): ProductResponse
    fun searchByKeyword(keyword: String): ProductResponse
    fun updateProduct(id: Long, productDTO: ProductDTO): ProductDTO
}
