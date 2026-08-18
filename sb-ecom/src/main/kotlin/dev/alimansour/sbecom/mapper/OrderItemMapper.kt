package dev.alimansour.sbecom.mapper

import dev.alimansour.sbecom.exception.APIException
import dev.alimansour.sbecom.model.OrderItem
import dev.alimansour.sbecom.payload.OrderItemDTO

fun OrderItem.toDto(): OrderItemDTO =
    OrderItemDTO(
        id = this.id,
        product = this.product?.toDTO() ?: throw APIException("Product is not set for order with id $id"),
        quantity = this.quantity,
        discount = this.discount,
        orderedProductPrice = this.orderedProductPrice,
    )

