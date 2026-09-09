package dev.alimansour.sbecom.payload

data class AnalyticsResponse(
    val totalUsers: Long = 0,
    val totalProducts: Long = 0,
    val totalOrders: Long = 0,
    val totalRevenue: Double = 0.0,
)