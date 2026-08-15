package dev.alimansour.sbecom.repository

import dev.alimansour.sbecom.model.CartItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CartItemRepository : JpaRepository<CartItem, Long> {
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = ?1 AND ci.product.id = ?2")
    fun findCartItemByProductIdAndCartId(cartId: Long, productId: Long): CartItem?
}