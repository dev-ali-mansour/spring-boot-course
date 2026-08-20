package com.project.app.controller

import com.project.app.model.Rating
import com.project.app.service.RatingService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/movies/{movieId}/ratings")
class RatingController(
    private val ratingService: RatingService
) {

    @PostMapping
    fun addRating(
        @PathVariable movieId: Long,
        @Valid @RequestBody rating: Rating
    ): ResponseEntity<Rating> {
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.addRating(movieId, rating))
    }

    @GetMapping
    fun getRatings(@PathVariable movieId: Long): ResponseEntity<List<Rating>> {
        return ResponseEntity.ok(ratingService.getRatingsForMovie(movieId))
    }

    @GetMapping("/average")
    fun getAverageRating(@PathVariable movieId: Long): ResponseEntity<Map<String, Any>> {
        val average = ratingService.getAverageRating(movieId)
        val response: Map<String, Any> = mapOf(
            "movieId" to movieId,
            "average" to (Math.round(average * 10.0) / 10.0)
        )
        return ResponseEntity.ok(response)
    }
}
