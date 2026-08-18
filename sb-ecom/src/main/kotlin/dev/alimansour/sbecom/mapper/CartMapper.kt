package dev.alimansour.sbecom.mapper

import dev.alimansour.sbecom.model.Cart
import dev.alimansour.sbecom.payload.CartDTO

fun Cart.toDTO(): CartDTO = CartDTO(
    id = this.id,
    totalPrice = this.totalPrice,
    products = this.cartItems.map { item ->
        requireNotNull(item.product) { "Cart item must not be null!" }
            .toDTO().copy(quantity = item.quantity)
    }
)