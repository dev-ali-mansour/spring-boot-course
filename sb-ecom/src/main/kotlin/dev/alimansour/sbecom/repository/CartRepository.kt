package dev.alimansour.sbecom.repository

import dev.alimansour.sbecom.model.Cart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.user.email= ?1")
    fun findCartByEmail(email: String): Cart?
}