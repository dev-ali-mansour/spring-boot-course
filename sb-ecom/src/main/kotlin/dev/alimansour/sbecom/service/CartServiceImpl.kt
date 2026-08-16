package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.exception.APIException
import dev.alimansour.sbecom.exception.ResourceNotFoundException
import dev.alimansour.sbecom.mapper.toDTO
import dev.alimansour.sbecom.model.Cart
import dev.alimansour.sbecom.model.CartItem
import dev.alimansour.sbecom.payload.CartDTO
import dev.alimansour.sbecom.repository.CartItemRepository
import dev.alimansour.sbecom.repository.CartRepository
import dev.alimansour.sbecom.repository.ProductRepository
import dev.alimansour.sbecom.util.AuthUtil
import org.springframework.stereotype.Service

@Service
class CartServiceImpl(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val cartItemRepository: CartItemRepository,
    private val authUtil: AuthUtil,
) : CartService {
    override fun addProductToCart(productId: Long, quantity: Int): CartDTO {
        val cart = createCart()

        val product = productRepository.findById(productId)
            .orElseThrow {
                ResourceNotFoundException(resourceName = "Product", field = "id", fieldId = productId)
            }

        val existingCartItem = cartItemRepository.findCartItemByProductIdAndCartId(cart.id!!, productId)

        if (existingCartItem != null) {
            throw APIException("Product ${product.name} already exists in the cart!")
        }

        if (product.quantity == 0) {
            throw APIException("Product ${product.name} is not available!")
        }

        if (product.quantity < quantity) {
            throw APIException(
                "Please make an order of ${product.name} " +
                        "less than or equal to the quantity ${product.quantity}!"
            )
        }

        val newCartItem = CartItem(
            cart = cart,
            product = product,
            quantity = quantity,
            discount = product.discount,
            price = product.specialPrice,
        )

        cartItemRepository.save(newCartItem)
        cart.cartItems.add(newCartItem)

        cart.totalPrice += (product.specialPrice * quantity)
        val updatedCart = cartRepository.save(cart)

        return updatedCart.toDTO()
    }

    private fun createCart(): Cart {
        cartRepository.findCartByEmail(authUtil.loggedInEmail())?.let { userCart ->
            return userCart
        }

        val cart = Cart(totalPrice = 0.0, user = authUtil.loggedInUser())
        return cartRepository.save(cart)
    }
}