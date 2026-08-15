package dev.alimansour.sbecom.mapper

import dev.alimansour.sbecom.model.Cart
import dev.alimansour.sbecom.payload.CartDTO

fun Cart.toDTO(): CartDTO = CartDTO(
    id = this.id,
    totalPrice = this.totalPrice,
    products = this.cartItems.map { item ->
        item.product!!.toDTO().copy(quantity = item.quantity)
    }
)