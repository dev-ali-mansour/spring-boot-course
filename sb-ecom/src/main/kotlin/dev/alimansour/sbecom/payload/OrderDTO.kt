package dev.alimansour.sbecom.payload

import jakarta.validation.constraints.Email
import java.time.LocalDate

data class OrderDTO(
    val id: Long? = null,
    @Email
    val email: String,
    val orderItems: List<OrderItemDTO>,
    val orderDate: LocalDate,
    val paymentDTO: PaymentDTO,
    val totalAmount: Double,
    val orderStatus: String,
    val addressId: Long,
)
