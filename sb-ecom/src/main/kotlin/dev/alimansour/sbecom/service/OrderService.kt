package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.payload.OrderDTO
import dev.alimansour.sbecom.payload.OrderRequestDTO
import dev.alimansour.sbecom.payload.OrderResponse
import org.springframework.data.domain.Pageable

interface OrderService {
    fun placeOrder(paymentMethod: String, orderRequestDTO: OrderRequestDTO): OrderDTO
    fun getAllOrders(pageable: Pageable): OrderResponse
}