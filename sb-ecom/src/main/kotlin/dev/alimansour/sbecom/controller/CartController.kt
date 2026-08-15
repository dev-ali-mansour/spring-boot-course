package dev.alimansour.sbecom.controller

import dev.alimansour.sbecom.payload.CartDTO
import dev.alimansour.sbecom.service.CartService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class CartController (private val cartService: CartService){
    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    fun addProductToCart(
        @PathVariable productId: Long,
        @PathVariable quantity: Int
    ): ResponseEntity<CartDTO> {
        val cartDTO = cartService.addProductToCart(productId, quantity)
        return ResponseEntity(cartDTO, HttpStatus.CREATED)
    }
}