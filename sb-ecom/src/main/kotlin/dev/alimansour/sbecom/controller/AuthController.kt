package dev.alimansour.sbecom.controller

import dev.alimansour.sbecom.config.AppConstants
import dev.alimansour.sbecom.payload.UsersResponse
import dev.alimansour.sbecom.security.request.SignInRequest
import dev.alimansour.sbecom.security.request.SignUpRequest
import dev.alimansour.sbecom.security.response.MessageResponse
import dev.alimansour.sbecom.security.response.UserInfoResponse
import dev.alimansour.sbecom.service.AuthService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/auth"])
class AuthController(private val authService: AuthService) {

    @Tag(name = "Authentication APIs", description = "APIs for user authentication")
    @PostMapping("/login")
    fun authenticateUser(@Validated @RequestBody signInRequest: SignInRequest): ResponseEntity<UserInfoResponse> {
        val result = authService.login(signInRequest)
        return ok()
            .header(HttpHeaders.SET_COOKIE, result.jwtCookie.toString())
            .body(result.userInfoResponse)
    }

    @Tag(name = "Authentication APIs", description = "APIs for user authentication")
    @PostMapping("/register")
    fun registerUser(@Validated @RequestBody signUpRequest: SignUpRequest): ResponseEntity<Any> {
        authService.register(signUpRequest)
        return ok(MessageResponse("User registered successfully"))
    }

    @Tag(name = "Authentication APIs", description = "APIs for user authentication")
    @GetMapping("/username")
    fun currentUsername(): String = authService.getCurrentUsername()

    @Tag(name = "Authentication APIs", description = "APIs for user authentication")
    @GetMapping("/user")
    fun getUserDetails(): ResponseEntity<UserInfoResponse> {
        val response = authService.getUserInfo()
        return ok().body(response)
    }

    @Tag(name = "Authentication APIs", description = "APIs for user authentication")
    @PostMapping("/logout")
    fun logoutUser(): ResponseEntity<MessageResponse> {
        val cookie = authService.logout()

        return ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(MessageResponse("You've been logged out!"))
    }

    @Tag(name = "Authentication APIs", description = "APIs for user authentication")
    @GetMapping("/sellers")
    fun getAllSellers(
        @PageableDefault(
            page = AppConstants.PAGE_NUMBER,
            size = AppConstants.PAGE_SIZE,
            sort = [AppConstants.SORT_USERS_BY],
            direction = Sort.Direction.ASC
        ) pageable: Pageable,
    ): ResponseEntity<UsersResponse> = ok().body(authService.getAllSellers(pageable))
}