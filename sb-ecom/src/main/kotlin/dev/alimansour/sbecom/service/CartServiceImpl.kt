package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.exception.APIException
import dev.alimansour.sbecom.exception.ResourceNotFoundException
import dev.alimansour.sbecom.mapper.toDTO
import dev.alimansour.sbecom.model.Cart
import dev.alimansour.sbecom.model.CartItem
import dev.alimansour.sbecom.model.Product
import dev.alimansour.sbecom.payload.CartDTO
import dev.alimansour.sbecom.repository.CartItemRepository
import dev.alimansour.sbecom.repository.CartRepository
import dev.alimansour.sbecom.repository.ProductRepository
import dev.alimansour.sbecom.util.AuthUtil
import jakarta.transaction.Transactional
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

    override fun getAllCarts(): List<CartDTO> =
        cartRepository.findAll().map { it.toDTO() }

    override fun getUserCart(): CartDTO {
        val userId = authUtil.loggedInUserId()
        val userCart = cartRepository.findCartByUserId(userId)
            ?: throw ResourceNotFoundException(resourceName = "Cart", field = "userId", fieldId = userId)

        return userCart.toDTO()
    }

    @Transactional
    override fun updateProductQuantityInCart(
        productId: Long,
        quantity: Int
    ): CartDTO {
        val userId = authUtil.loggedInUserId()
        val userCart = cartRepository.findCartByUserId(userId)
            ?: throw ResourceNotFoundException(resourceName = "Cart", field = "userId", fieldId = userId)

        val product = productRepository.findById(productId)
            .orElseThrow { ResourceNotFoundException(resourceName = "Product", field = "id", fieldId = productId) }

        val cartItem = cartItemRepository.findCartItemByProductIdAndCartId(userCart.id!!, productId)
            ?: throw APIException("Product ${product.name} does not exist in the cart!")

        val newQuantity = cartItem.quantity + quantity

        if (quantity > 0) {
            if (product.quantity == 0) {
                throw APIException("Product ${product.name} is not available!")
            }
            if (newQuantity > product.quantity) {
                throw APIException(
                    "Please make an order of ${product.name} " +
                            "less than or equal to the quantity ${product.quantity}!"
                )
            }
        }

        if (newQuantity < 0) {
            throw APIException("Cannot reduce quantity below zero!")
        }

        val updatedCart = userCart.updateItemQuantity(
            cartItem = cartItem,
            product = product,
            newQuantity = newQuantity
        )
        return updatedCart.toDTO()
    }

    @Transactional
    override fun deleteProductFromCart(productId: Long): String {
        val userId = authUtil.loggedInUserId()
        val userCart = cartRepository.findCartByUserId(userId)
            ?: throw ResourceNotFoundException(resourceName = "Cart", field = "userId", fieldId = userId)

        val product = productRepository.findById(productId)
            .orElseThrow { ResourceNotFoundException(resourceName = "Product", field = "id", fieldId = productId) }

        val cartItem = cartItemRepository.findCartItemByProductIdAndCartId(userCart.id!!, productId)
            ?: throw APIException("Product ${product.name} does not exist in the cart!")

        userCart.updateItemQuantity(cartItem = cartItem, product = product, newQuantity = 0)

        return "Product ${product.name} has been removed from the cart!"
    }

    @Transactional
    override fun deleteProductFromAllCarts(productId: Long) {
        val carts = cartRepository.findCartByProductId(productId)
        if (carts.isEmpty()) return

        val product = productRepository.findById(productId)
            .orElseThrow { ResourceNotFoundException(resourceName = "Product", field = "id", fieldId = productId) }

        carts.forEach { cart ->
            val cartItem = cart.cartItems.find { it.product?.id == productId }
            cartItem?.let { item ->
                cart.updateItemQuantity(cartItem = item, product = product, newQuantity = 0)
            }
        }
    }

    @Transactional
    override fun updateProductInCarts(cartId: Long, productId: Long) {
        val cart = cartRepository.findById(cartId)
            .orElseThrow { ResourceNotFoundException(resourceName = "Cart", field = "id", fieldId = cartId) }

        val product = productRepository.findById(productId)
            .orElseThrow { ResourceNotFoundException(resourceName = "Product", field = "id", fieldId = productId) }

        val cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId)
            ?: throw APIException("Product ${product.name} does not exist in the cart!")

        val oldItemPrice = cartItem.price * cartItem.quantity
        cartItem.price = product.specialPrice
        val newItemPrice = cartItem.price * cartItem.quantity

        cart.totalPrice += (newItemPrice - oldItemPrice)

        cartItemRepository.save(cartItem)
        cartRepository.save(cart)
    }

    private fun Cart.updateItemQuantity(cartItem: CartItem, product: Product, newQuantity: Int): Cart {
        if (newQuantity == 0) {
            cartItemRepository.deleteById(cartItem.id!!)
            cartItems.removeIf { it.id == cartItem.id }
        } else {
            cartItem.price = product.specialPrice
            cartItem.quantity = newQuantity
            cartItem.discount = product.discount
            cartItemRepository.save(cartItem)
        }

        totalPrice = cartItems.sumOf { it.price * it.quantity }
        return cartRepository.save(this)
    }

    private fun createCart(): Cart {
        cartRepository.findCartByUserId(authUtil.loggedInUserId())?.let { userCart ->
            return userCart
        }

        val cart = Cart(totalPrice = 0.0, user = authUtil.loggedInUser())
        return cartRepository.save(cart)
    }
}