package dev.alimansour.sbecom.controller

import dev.alimansour.sbecom.payload.CartDTO
import dev.alimansour.sbecom.service.CartService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class CartController(private val cartService: CartService) {

    @Tag(name = "Cart APIs", description = "APIs for managing carts")
    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    fun addProductToCart(
        @PathVariable productId: Long,
        @PathVariable quantity: Int
    ): ResponseEntity<CartDTO> {
        val cartDTO = cartService.addProductToCart(productId, quantity)
        return ResponseEntity(cartDTO, HttpStatus.CREATED)
    }

    @Tag(name = "Cart APIs", description = "APIs for managing carts")
    @GetMapping("/carts")
    fun getCarts(): ResponseEntity<List<CartDTO>> =
        ResponseEntity(cartService.getAllCarts(), HttpStatus.OK)

    @Tag(name = "Cart APIs", description = "APIs for managing carts")
    @GetMapping("/carts/users/cart")
    fun getUserCart(): ResponseEntity<CartDTO> =
        ResponseEntity(cartService.getUserCart(), HttpStatus.OK)

    @Tag(name = "Cart APIs", description = "APIs for managing carts")
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

    @Tag(name = "Cart APIs", description = "APIs for managing carts")
    @DeleteMapping("/carts/products/{productId}")
    fun deleteProductFromCart(@PathVariable productId: Long): ResponseEntity<String> {
        val status = cartService.deleteProductFromCurrentUserCart(productId)
        return ResponseEntity(status, HttpStatus.OK)
    }
}