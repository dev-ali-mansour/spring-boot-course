package dev.alimansour.sbecom.mapper

import dev.alimansour.sbecom.exception.APIException
import dev.alimansour.sbecom.model.Order
import dev.alimansour.sbecom.payload.OrderDTO

fun Order.toDTO(): OrderDTO =
    OrderDTO(
        id = this.id,
        email = this.email,
        orderItems = this.orderItems.map { it.toDto() },
        orderDate = this.orderDate,
        paymentDTO = this.payment.toDTO(),
        totalAmount = this.totalAmount,
        orderStatus = this.orderStatus,
        addressId = this.address?.id
            ?: throw APIException("Address is not set to order with id $id"),
    )