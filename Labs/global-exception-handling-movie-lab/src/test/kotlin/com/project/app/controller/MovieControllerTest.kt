package com.project.app.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.project.app.model.Movie
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
class MovieControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val objectMapper = ObjectMapper()

    @Test
    fun getAllMovies_returnsAllSeededMovies() {
        mockMvc.perform(get("/api/movies"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Any>(5)))
            .andExpect(jsonPath("$[0].title", `is`("Inception")))
    }

    @Test
    fun getMovieById_existingMovie_returnsMovie() {
        mockMvc.perform(get("/api/movies/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id", `is`(1)))
            .andExpect(jsonPath("$.title", `is`("Inception")))
            .andExpect(jsonPath("$.genre", `is`("sci-fi")))
    }

    @Test
    fun getMovieById_nonExistentMovie_returns404() {
        mockMvc.perform(get("/api/movies/9999"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status", `is`(404)))
            .andExpect(jsonPath("$.message", `is`("Movie not found with id: 9999")))
    }

    @Test
    fun addMovie_validPayload_returnsCreatedMovie() {
        val newMovie = Movie(title = "The Matrix", genre = "sci-fi")

        mockMvc.perform(
            post("/api/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newMovie))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.title", `is`("The Matrix")))
            .andExpect(jsonPath("$.genre", `is`("sci-fi")))
    }

    @Test
    fun addMovie_blankTitle_returns400() {
        val invalidMovie = mapOf("title" to "", "genre" to "sci-fi")

        mockMvc.perform(
            post("/api/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidMovie))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status", `is`(400)))
            .andExpect(jsonPath("$.message", `is`("Title is required")))
    }

    @Test
    fun addMovie_duplicateTitle_returns409() {
        val duplicateMovie = Movie(title = "Inception", genre = "sci-fi")

        mockMvc.perform(
            post("/api/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(duplicateMovie))
        )
            .andExpect(status().isConflict)
            .andExpect(jsonPath("$.status", `is`(409)))
            .andExpect(jsonPath("$.message", `is`("Movie with title 'Inception' already exists")))
    }
}
