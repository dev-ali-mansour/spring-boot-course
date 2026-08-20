package com.project.app.service

import com.project.app.exception.MovieAlreadyExistsException
import com.project.app.exception.MovieNotFoundException
import com.project.app.model.Movie
import com.project.app.repository.MovieRepository
import org.springframework.stereotype.Service

@Service
class MovieService(
    private val movieRepository: MovieRepository
) {

    fun addMovie(movie: Movie): Movie {
        if (movieRepository.existsByTitleIgnoreCase(movie.title)) {
            throw MovieAlreadyExistsException(movie.title)
        }
        return movieRepository.save(movie)
    }

    fun getAllMovies(): List<Movie> {
        return movieRepository.findAll()
    }

    fun getMovieById(id: Long): Movie {
        return movieRepository.findById(id)
            .orElseThrow { MovieNotFoundException(id) }
    }
}
