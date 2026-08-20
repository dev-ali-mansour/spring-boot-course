package com.project.app.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank

@Entity
class Movie(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotBlank(message = "Title is required")
    var title: String = "",

    @field:NotBlank(message = "Genre is required")
    var genre: String = ""
) {
    constructor(title: String, genre: String) : this(null, title, genre)
}
