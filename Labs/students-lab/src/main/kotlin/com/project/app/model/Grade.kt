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
class Grade(
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    var student: Student? = null,

    @field:NotBlank(message = "Subject is required")
    var subject: String = "",

    @field:Min(value = 0, message = "Score must be at least 0")
    @field:Max(value = 100, message = "Score must be at most 100")
    var score: Int = 0,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)
