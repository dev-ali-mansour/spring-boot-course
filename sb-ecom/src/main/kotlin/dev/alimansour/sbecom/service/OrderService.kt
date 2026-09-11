package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.payload.OrderDTO
import dev.alimansour.sbecom.payload.OrderRequestDTO
import dev.alimansour.sbecom.payload.OrderResponse
import dev.alimansour.sbecom.payload.OrderStatusUpdateDTO
import org.springframework.data.domain.Pageable

interface OrderService {
    fun placeOrder(paymentMethod: String, orderRequestDTO: OrderRequestDTO): OrderDTO
    fun getAllOrders(pageable: Pageable): OrderResponse
    fun updateOrderStatus(orderId: Long, orderStatusUpdateDTO: OrderStatusUpdateDTO): OrderDTO
    fun updateSellerOrderStatus(orderId: Long, orderStatusUpdateDTO: OrderStatusUpdateDTO): OrderDTO
    fun getAllSellerOrders(pageable: Pageable): OrderResponse
}