package dev.alimansour.sbecom.repository

import dev.alimansour.sbecom.model.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long>