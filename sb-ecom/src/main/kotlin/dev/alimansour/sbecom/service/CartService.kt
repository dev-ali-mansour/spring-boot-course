package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.payload.CartDTO
import dev.alimansour.sbecom.payload.CartItemDTO

interface CartService {
    fun addProductToCart(productId: Long, quantity: Int): CartDTO
    fun getAllCarts(): List<CartDTO>
    fun getUserCart(userId: Long): CartDTO?
    fun getCurrentUserCart(): CartDTO
    fun updateProductQuantityInCart(productId: Long, quantity: Int): CartDTO
    fun deleteProductFromCart(cartId: Long, productId: Long): String
    fun deleteProductFromCurrentUserCart(productId: Long): String
    fun deleteProductFromAllCarts(productId: Long)
    fun updateProductInCarts(cartId: Long, productId: Long)
    fun createOrUpdateCartWithItems(cartItems: List<CartItemDTO>): CartDTO
}