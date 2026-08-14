package dev.alimansour.sbecom.security.response

data class UserInfoResponse(
    val id: Long?,
    val jwtToken: String,
    val username: String,
    val roles: List<String>,
)