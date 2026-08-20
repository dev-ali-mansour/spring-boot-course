package com.project.app.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.project.app.model.Rating
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RatingControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val objectMapper = ObjectMapper()

    @Test
    fun getRatings_returnsMovieRatings() {
        mockMvc.perform(get("/api/movies/1/ratings"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Any>(1)))
            .andExpect(jsonPath("$[0].reviewer", `is`("Alice")))
            .andExpect(jsonPath("$[0].stars", `is`(5)))
    }

    @Test
    fun addRating_validPayload_createsRating() {
        val rating = Rating(movie = null, stars = 4, reviewer = "Dave")

        mockMvc.perform(
            post("/api/movies/1/ratings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rating))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.stars", `is`(4)))
            .andExpect(jsonPath("$.reviewer", `is`("Dave")))
    }

    @Test
    fun addRating_invalidStars_returns400() {
        val ratingPayload = mapOf("stars" to 9, "reviewer" to "Charlie")

        mockMvc.perform(
            post("/api/movies/1/ratings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ratingPayload))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status", `is`(400)))
            .andExpect(jsonPath("$.message", `is`("Rating must be at most 5")))
    }

    @Test
    fun addRating_duplicateReviewer_returns409() {
        val duplicateRating = Rating(movie = null, stars = 3, reviewer = "Alice")

        mockMvc.perform(
            post("/api/movies/1/ratings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(duplicateRating))
        )
            .andExpect(status().isConflict)
            .andExpect(jsonPath("$.status", `is`(409)))
            .andExpect(jsonPath("$.message", `is`("Reviewer 'Alice' has already rated movie with id: 1")))
    }

    @Test
    fun addRating_nonExistentMovie_returns404() {
        val rating = Rating(movie = null, stars = 3, reviewer = "Dave")

        mockMvc.perform(
            post("/api/movies/9999/ratings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rating))
        )
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status", `is`(404)))
            .andExpect(jsonPath("$.message", `is`("Movie not found with id: 9999")))
    }

    @Test
    fun getAverageRating_withRatings_returnsAverage() {
        mockMvc.perform(get("/api/movies/1/ratings/average"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.movieId", `is`(1)))
            .andExpect(jsonPath("$.average", `is`(5.0)))
    }

    @Test
    fun getAverageRating_withoutRatings_returnsZero() {
        mockMvc.perform(get("/api/movies/3/ratings/average"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.movieId", `is`(3)))
            .andExpect(jsonPath("$.average", `is`(0.0)))
    }

    @Test
    fun getAverageRating_nonExistentMovie_returns404() {
        mockMvc.perform(get("/api/movies/9999/ratings/average"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status", `is`(404)))
            .andExpect(jsonPath("$.message", `is`("Movie not found with id: 9999")))
    }
}
