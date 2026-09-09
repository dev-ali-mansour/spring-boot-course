package dev.alimansour.sbecom.controller

import com.stripe.model.PaymentIntent
import dev.alimansour.sbecom.payload.OrderDTO
import dev.alimansour.sbecom.payload.OrderRequestDTO
import dev.alimansour.sbecom.payload.StripePaymentDTO
import dev.alimansour.sbecom.service.OrderService
import dev.alimansour.sbecom.service.StripeService
import io.swagger.v3.oas.annotations.tags.Tag
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
}