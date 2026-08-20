package com.project.app.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank

@Entity
class Student(
    @field:NotBlank(message = "Name is required")
    var name: String = "",

    @field:NotBlank(message = "Email is required")
    var email: String = "",

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)
