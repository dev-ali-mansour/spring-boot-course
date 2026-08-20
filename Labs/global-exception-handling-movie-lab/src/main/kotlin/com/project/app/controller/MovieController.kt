package com.project.app.controller

import com.project.app.model.Movie
import com.project.app.service.MovieService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/movies")
class MovieController(
    private val movieService: MovieService
) {
    @PostMapping
    fun addMovie(@Valid @RequestBody movie: Movie): ResponseEntity<Movie> {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.addMovie(movie))
    }

    @GetMapping
    fun getAllMovies(): ResponseEntity<List<Movie>> {
        return ResponseEntity.ok(movieService.getAllMovies())
    }

    @GetMapping("/{id}")
    fun getMovieById(@PathVariable id: Long): ResponseEntity<Movie> {
        return ResponseEntity.ok(movieService.getMovieById(id))
    }
}
