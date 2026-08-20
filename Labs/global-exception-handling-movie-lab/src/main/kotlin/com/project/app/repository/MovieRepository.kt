package com.project.app.repository

import com.project.app.model.Movie
import org.springframework.data.jpa.repository.JpaRepository

interface MovieRepository : JpaRepository<Movie, Long> {
    fun existsByTitleIgnoreCase(title: String): Boolean
}
