package dev.alimansour.sbecom.security.jwt

data class LoginResponse(
    val jwtToken: String,
    val username: String,
    val roles: List<String>,
)