package dev.alimansour.sbecom.payload

import dev.alimansour.sbecom.security.response.UserInfoResponse
import org.springframework.http.ResponseCookie

data class AuthenticationResult(
    val userInfoResponse: UserInfoResponse,
    val jwtCookie: ResponseCookie,
)
