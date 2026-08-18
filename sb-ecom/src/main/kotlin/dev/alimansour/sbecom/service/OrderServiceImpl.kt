package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.exception.APIException
import dev.alimansour.sbecom.exception.ResourceNotFoundException
import dev.alimansour.sbecom.mapper.toDTO
import dev.alimansour.sbecom.mapper.toDto
import dev.alimansour.sbecom.model.*
import dev.alimansour.sbecom.payload.OrderDTO
import dev.alimansour.sbecom.payload.OrderRequestDTO
import dev.alimansour.sbecom.repository.*
import dev.alimansour.sbecom.util.AuthUtil
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class OrderServiceImpl(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    private val addressRepository: AddressRepository,
    private val paymentRepository: PaymentRepository,
    private val orderItemRepository: OrderItemRepository,
    private val productRepository: ProductRepository,
    private val cartService: CartService,
    private val authUtil: AuthUtil,
) : OrderService {

    @Transactional
    override fun placeOrder(
        paymentMethod: String,
        orderRequestDTO: OrderRequestDTO
    ): OrderDTO {
        val userId = authUtil.loggedInUserId()
        val email = authUtil.loggedInEmail()
        val addressId = orderRequestDTO.addressId
        val pgName = orderRequestDTO.pgName
        val pgPaymentId = orderRequestDTO.pgPaymentId
        val pgStatus = orderRequestDTO.pgStatus
        val pgResponseMessage = orderRequestDTO.pgResponseMessage

        val cart = cartRepository.findCartByUserId(userId)
            ?: throw ResourceNotFoundException(resourceName = "Cart", field = "userId", fieldId = userId)

        val address = addressRepository.findById(addressId)
            .orElseThrow {
                ResourceNotFoundException(resourceName = "Address", field = "addressId", fieldId = addressId)
            }

        var payment = Payment(
            paymentMethod = paymentMethod,
            pgPaymentId = pgPaymentId,
            pgStatus = pgStatus,
            pgResponseMessage = pgResponseMessage,
            pgName = pgName,
        )

        val order = Order(
            email = email,
            orderDate = LocalDate.now(),
            payment = payment,
            totalAmount = cart.totalPrice,
            orderStatus = "Order Accepted!",
            address = address,
        )

        payment = paymentRepository.save(payment)
        order.payment = payment

        val savedOrder = orderRepository.save(order)

        val cartItems = cart.cartItems
        if (cartItems.isEmpty()) {
            throw APIException("Cart is empty!")
        }

        val orderItems = saveOrderItems(cartItems, savedOrder)

        cart.updateProductQuantityAndDelete()

        val orderDto = savedOrder.toDTO().copy(orderItems = orderItems.map { it.toDto() })

        return orderDto
    }

    @Transactional
    fun Cart.updateProductQuantityAndDelete() {
        val cartItemsCopy = cartItems.toList()
        cartItemsCopy.forEach { cartItem ->
            val product = cartItem.product ?: throw APIException("The cart item product is not found!")
            product.quantity -= cartItem.quantity
            productRepository.save(product)

            cartService.deleteProductFromCart(
                cartId = requireNotNull(id) { "Cart ID must not be null" }, 
                productId = requireNotNull(product.id) { "Product ID must not be null" }
            )
        }
    }

    @Transactional
    fun saveOrderItems(
        cartItems: MutableList<CartItem>,
        order: Order
    ): List<OrderItem> {
        val orderItems = cartItems.map { cartItem ->
            OrderItem(
                product = cartItem.product,
                quantity = cartItem.quantity,
                discount = cartItem.discount,
                orderedProductPrice = cartItem.price,
                order = order,
            )
        }

        return orderItemRepository.saveAll(orderItems)
    }

}