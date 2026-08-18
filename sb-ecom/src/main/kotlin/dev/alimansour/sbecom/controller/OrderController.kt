package dev.alimansour.sbecom.controller

import dev.alimansour.sbecom.payload.OrderDTO
import dev.alimansour.sbecom.payload.OrderRequestDTO
import dev.alimansour.sbecom.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class OrderController(private val orderService: OrderService) {
    @PostMapping("/orders/users/payments/{paymentMethod}")
    fun orderProducts(
        @PathVariable paymentMethod: String,
        @Validated @RequestBody orderRequestDTO: OrderRequestDTO
    ): ResponseEntity<OrderDTO> {
        val order: OrderDTO = orderService.placeOrder(paymentMethod, orderRequestDTO)
        return ResponseEntity(order, HttpStatus.CREATED)
    }
}