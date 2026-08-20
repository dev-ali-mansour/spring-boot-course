package com.project.app.repository

import com.project.app.model.Rating
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface RatingRepository : JpaRepository<Rating, Long> {

    fun findByMovieId(movieId: Long): List<Rating>

    fun existsByMovieIdAndReviewer(movieId: Long, reviewer: String): Boolean

    @Query("SELECT AVG(r.stars) FROM Rating r WHERE r.movie.id = :movieId")
    fun findAverageStarsByMovieId(@Param("movieId") movieId: Long): Optional<Double>
}
