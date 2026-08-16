package dev.alimansour.sbecom.repository

import dev.alimansour.sbecom.model.Cart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    fun findCartByUserId(@Param("userId") userId: Long): Cart?

    @Query("SELECT DISTINCT c FROM Cart c JOIN FETCH c.cartItems ci WHERE c.id IN (SELECT c2.id FROM Cart c2 JOIN c2.cartItems ci2 WHERE ci2.product.id = :id)")
    fun findCartByProductId(@Param("id") id: Long): List<Cart>
}