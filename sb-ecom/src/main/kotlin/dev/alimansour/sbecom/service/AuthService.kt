package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.payload.AuthenticationResult
import dev.alimansour.sbecom.payload.UsersResponse
import dev.alimansour.sbecom.security.request.SignInRequest
import dev.alimansour.sbecom.security.request.SignUpRequest
import dev.alimansour.sbecom.security.response.UserInfoResponse
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseCookie

interface AuthService {
    fun login(signInRequest: SignInRequest): AuthenticationResult
    fun register(signUpRequest: SignUpRequest)
    fun getCurrentUsername(): String
    fun getUserInfo():UserInfoResponse
    fun logout():ResponseCookie
    fun getAllSellers(pageable: Pageable): UsersResponse
}