package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.payload.OrderDTO
import dev.alimansour.sbecom.payload.OrderRequestDTO

interface OrderService {
    fun placeOrder(paymentMethod: String, orderRequestDTO: OrderRequestDTO): OrderDTO

}