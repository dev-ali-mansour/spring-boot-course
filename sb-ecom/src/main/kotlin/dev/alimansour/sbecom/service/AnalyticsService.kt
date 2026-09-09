package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.payload.AnalyticsResponse

interface AnalyticsService {
    fun getAnalyticsData(): AnalyticsResponse
}