package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.payload.CartDTO

interface CartService {
    fun addProductToCart(productId: Long, quantity: Int): CartDTO
}
