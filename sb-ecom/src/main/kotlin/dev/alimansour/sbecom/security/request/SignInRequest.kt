package dev.alimansour.sbecom.security.request

import jakarta.validation.constraints.NotBlank

data class SignInRequest(
    @NotBlank
    val username: String,

    @NotBlank
    val password: String,
)