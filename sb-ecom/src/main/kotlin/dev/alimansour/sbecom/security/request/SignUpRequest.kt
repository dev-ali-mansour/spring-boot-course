package dev.alimansour.sbecom.security.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class SignUpRequest(
    @NotBlank
    @Size(min = 3, max = 20)
    val username: String,

    @NotBlank
    @Email
    val email: String,

    val roles: Set<String>?,

    @NotBlank
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "Password must be at least 8 characters, contain at least one lowercase, at least one uppercase, at least one digit, and at least one special character!"
    )
    val password: String,
)