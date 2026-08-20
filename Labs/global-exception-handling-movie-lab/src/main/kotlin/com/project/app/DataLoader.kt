package com.project.app

import com.project.app.model.Movie
import com.project.app.model.Rating
import com.project.app.repository.MovieRepository
import com.project.app.repository.RatingRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataLoader(
    private val movieRepository: MovieRepository,
    private val ratingRepository: RatingRepository
) : CommandLineRunner {

    override fun run(vararg args: String) {
        val inception = movieRepository.save(Movie("Inception", "sci-fi"))
        val darkKnight = movieRepository.save(Movie("The Dark Knight", "action"))
        movieRepository.save(Movie("Interstellar", "sci-fi"))
        movieRepository.save(Movie("Parasite", "thriller"))
        movieRepository.save(Movie("The Shawshank Redemption", "drama"))

        ratingRepository.save(Rating(inception, 5, "Alice"))
        ratingRepository.save(Rating(darkKnight, 4, "Bob"))
    }
}
