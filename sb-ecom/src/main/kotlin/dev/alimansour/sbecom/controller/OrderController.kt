package dev.alimansour.sbecom.controller

import com.stripe.model.PaymentIntent
import dev.alimansour.sbecom.config.AppConstants
import dev.alimansour.sbecom.payload.*
import dev.alimansour.sbecom.service.OrderService
import dev.alimansour.sbecom.service.StripeService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class OrderController(
    private val orderService: OrderService,
    private val stripeService: StripeService
) {

    @Tag(name = "Order APIs", description = "APIs for managing orders")
    @PostMapping("/orders/users/payments/{paymentMethod}")
    fun orderProducts(
        @PathVariable paymentMethod: String,
        @Validated @RequestBody orderRequestDTO: OrderRequestDTO
    ): ResponseEntity<OrderDTO> {
        val order: OrderDTO = orderService.placeOrder(paymentMethod, orderRequestDTO)
        return ResponseEntity(order, HttpStatus.CREATED)
    }

    @Tag(name = "Order APIs", description = "APIs for managing orders")
    @PostMapping("/orders/stripe-client-secret")
    fun createStripeClientSecret(
        @Validated @RequestBody stripePaymentDTO: StripePaymentDTO
    ): ResponseEntity<String> {
        val paymentIntent: PaymentIntent = stripeService.createPaymentIntent(stripePaymentDTO)
        return ResponseEntity(paymentIntent.clientSecret, HttpStatus.CREATED)
    }

    @GetMapping("/admin/orders")
    fun getAllOrders(
        @PageableDefault(
            page = AppConstants.PAGE_NUMBER,
            size = AppConstants.PAGE_SIZE,
            sort = [AppConstants.SORT_ORDERS_BY],
            direction = Sort.Direction.ASC
        ) pageable: Pageable,
    ): ResponseEntity<OrderResponse> {
        val orders: OrderResponse = orderService.getAllOrders(pageable)
        return ResponseEntity(orders, HttpStatus.OK)
    }

    @PutMapping("/admin/orders/{orderId}/status")
    fun updateOrderStatus(
        @PathVariable orderId: Long,
        @RequestBody orderStatusUpdateDTO: OrderStatusUpdateDTO,
    ): ResponseEntity<OrderDTO> {
        val updatedOrder: OrderDTO = orderService.updateOrder(orderId, orderStatusUpdateDTO)
        return ResponseEntity(updatedOrder, HttpStatus.OK)
    }
}