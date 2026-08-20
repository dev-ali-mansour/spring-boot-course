package com.project.app.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

@Entity
class Rating(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    var movie: Movie? = null,

    @field:Min(value = 1, message = "Rating must be at least 1")
    @field:Max(value = 5, message = "Rating must be at most 5")
    var stars: Int = 0,

    @field:NotBlank(message = "Reviewer name is required")
    var reviewer: String = ""
) {
    constructor(movie: Movie?, stars: Int, reviewer: String) : this(null, movie, stars, reviewer)
}
