package dev.alimansour.securitydemo.jwt

data class LoginRequest(
    val username: String,
    val password: String,
)