package dev.alimansour.sbecom.security.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class SignUpRequest(
    @NotBlank
    @Size(min = 2, max = 30, message = "Username must be between 2 and 30 characters!")
    val firstName: String,
    @NotBlank
    @Size(min = 2, max = 30, message = "Username must be between 2 and 30 characters!")
    val lastName: String,
    @NotBlank
    @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters!")
    val username: String,

    @NotBlank
    @Size(max = 50, message = "Email must not be grater than 50 characters!")
    @Email
    val email: String,

    val roles: Set<String>?,

    @NotBlank
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,120}$",
        message = "Password must be at least 8 characters, contain at least one lowercase, at least one uppercase, at least one digit, and at least one special character!"
    )
    val password: String,
)