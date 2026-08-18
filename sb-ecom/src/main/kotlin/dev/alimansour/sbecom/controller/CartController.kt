package dev.alimansour.sbecom.controller

import dev.alimansour.sbecom.payload.CartDTO
import dev.alimansour.sbecom.service.CartService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class CartController(private val cartService: CartService) {
    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    fun addProductToCart(
        @PathVariable productId: Long,
        @PathVariable quantity: Int
    ): ResponseEntity<CartDTO> {
        val cartDTO = cartService.addProductToCart(productId, quantity)
        return ResponseEntity(cartDTO, HttpStatus.CREATED)
    }

    @GetMapping("/carts")
    fun getCarts(): ResponseEntity<List<CartDTO>> =
        ResponseEntity(cartService.getAllCarts(), HttpStatus.OK)

    @GetMapping("/carts/users/cart")
    fun getUserCart(): ResponseEntity<CartDTO> =
        ResponseEntity(cartService.getUserCart(), HttpStatus.OK)

    @PutMapping("/carts/products/{productId}/quantity/{operation}")
    fun updateCartProduct(
        @PathVariable productId: Long,
        @PathVariable operation: String
    ): ResponseEntity<CartDTO> {
        val cartDTO: CartDTO = cartService.updateProductQuantityInCart(
            productId = productId,
            quantity = if (operation.equals("delete", ignoreCase = true)) -1 else 1
        )
        return ResponseEntity(cartDTO, HttpStatus.OK)
    }

    @DeleteMapping("/carts/products/{productId}")
    fun deleteProductFromCart(@PathVariable productId: Long): ResponseEntity<String> {
        val status = cartService.deleteProductFromCurrentUserCart(productId)
        return ResponseEntity(status, HttpStatus.OK)
    }
}