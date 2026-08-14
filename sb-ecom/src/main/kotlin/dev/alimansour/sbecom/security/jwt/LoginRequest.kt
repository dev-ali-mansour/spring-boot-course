package dev.alimansour.sbecom.security.jwt

data class LoginRequest(
    val username: String,
    val password: String,
)