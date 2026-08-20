package com.project.app.service

import com.project.app.exception.DuplicateRatingException
import com.project.app.exception.MovieNotFoundException
import com.project.app.model.Rating
import com.project.app.repository.MovieRepository
import com.project.app.repository.RatingRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class RatingService(
    private val ratingRepository: RatingRepository,
    private val movieRepository: MovieRepository
) {

    fun addRating(movieId: Long, rating: Rating): Rating {
        val movie = movieRepository.findById(movieId)
            .orElseThrow { MovieNotFoundException(movieId) }

        if (ratingRepository.existsByMovieIdAndReviewer(movieId, rating.reviewer)) {
            throw DuplicateRatingException(rating.reviewer, movieId)
        }

        rating.movie = movie
        return ratingRepository.save(rating)
    }

    fun getRatingsForMovie(movieId: Long): List<Rating> {
        return ratingRepository.findByMovieId(movieId)
    }

    fun getAverageRating(movieId: Long): Double {
        if (!movieRepository.existsById(movieId)) {
            throw MovieNotFoundException(movieId)
        }
        return ratingRepository.findAverageStarsByMovieId(movieId)
            .getOrElse { 0.0 }
    }
}
