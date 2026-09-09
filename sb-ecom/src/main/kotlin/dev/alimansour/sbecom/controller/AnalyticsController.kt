package dev.alimansour.sbecom.controller

import dev.alimansour.sbecom.payload.AnalyticsResponse
import dev.alimansour.sbecom.service.AnalyticsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class AnalyticsController(private val analyticsService: AnalyticsService) {

    @RequestMapping("/admin/app/analytics")
    fun getAnalytics(): ResponseEntity<AnalyticsResponse> {
        val response = analyticsService.getAnalyticsData()
        return ResponseEntity(response, HttpStatus.OK)
    }
}