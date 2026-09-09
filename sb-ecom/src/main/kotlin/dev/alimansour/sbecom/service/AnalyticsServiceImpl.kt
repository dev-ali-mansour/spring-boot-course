package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.payload.AnalyticsResponse
import dev.alimansour.sbecom.repository.OrderRepository
import dev.alimansour.sbecom.repository.ProductRepository
import dev.alimansour.sbecom.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AnalyticsServiceImpl(
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository,
) : AnalyticsService {
    override fun getAnalyticsData(): AnalyticsResponse {
        return AnalyticsResponse(
            totalUsers = userRepository.count(),
            totalProducts = productRepository.count(),
            totalOrders = orderRepository.count(),
            totalRevenue = orderRepository.getTotalRevenue()
        )
    }
}