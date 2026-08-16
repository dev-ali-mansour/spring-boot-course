package dev.alimansour.sbecom.repository

import dev.alimansour.sbecom.model.CartItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CartItemRepository : JpaRepository<CartItem, Long> {
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.id = :productId")
    fun findCartItemByProductIdAndCartId(@Param("cartId") cartId: Long, @Param("productId") productId: Long): CartItem?
}