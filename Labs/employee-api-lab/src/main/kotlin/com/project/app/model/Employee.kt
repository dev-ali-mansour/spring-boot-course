package com.project.app.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

@Entity
class Employee(
    @field:NotBlank(message = "First name is required")
    var firstName: String = "",

    @field:NotBlank(message = "Last name is required")
    var lastName: String = "",

    @field:NotBlank(message = "Department is required")
    var department: String = "",

    @field:Positive(message = "Salary must be positive")
    var salary: Double = 0.0,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)
