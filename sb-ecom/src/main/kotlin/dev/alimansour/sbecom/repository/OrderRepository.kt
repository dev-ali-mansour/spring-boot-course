package dev.alimansour.sbecom.repository

import dev.alimansour.sbecom.model.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long> {
    @Query("SELECT COALESCE( SUM(o.totalAmount),0) FROM Order o")
    fun getTotalRevenue(): Double
}