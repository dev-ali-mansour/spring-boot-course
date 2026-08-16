package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.payload.CartDTO

interface CartService {
    fun addProductToCart(productId: Long, quantity: Int): CartDTO
    fun getAllCarts(): List<CartDTO>
    fun getUserCart(): CartDTO
    fun updateProductQuantityInCart(productId: Long, quantity: Int): CartDTO
    fun deleteProductFromCart(productId: Long): String
}