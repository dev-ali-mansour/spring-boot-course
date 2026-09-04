package dev.alimansour.sbecom.security.response

data class UserInfoResponse(
    val id: Long?,
    val firstName: String,
    val lastName: String,
    val jwtToken: String? = null,
    val username: String,
    val roles: List<String>,
)