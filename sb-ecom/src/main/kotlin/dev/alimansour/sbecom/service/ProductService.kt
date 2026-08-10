package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.model.Product
import dev.alimansour.sbecom.payload.ProductDTO
import dev.alimansour.sbecom.payload.ProductResponse

interface ProductService {
    fun addProduct(categoryId: Long, product: Product): ProductDTO
    fun getAllProducts(): ProductResponse
}
