package dev.alimansour.sbecom.repository

import dev.alimansour.sbecom.model.Cart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.user.email= :email")
    fun findCartByEmail(@Param("email") email: String): Cart?
}