package dev.alimansour.sbecom.controller

import dev.alimansour.sbecom.security.jwt.JwtUtils
import dev.alimansour.sbecom.security.request.SignInRequest
import dev.alimansour.sbecom.security.response.UserInfoResponse
import dev.alimansour.sbecom.security.service.UserDetailsImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

@RestController
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils,
) {

    @PostMapping("/signin")
    fun authenticateUser(@Validated @RequestBody signInRequest: SignInRequest): ResponseEntity<Any> {
        val authentication: Authentication
        try {
            authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    signInRequest.username,
                    signInRequest.password
                )
            )
        } catch (_: AuthenticationException) {
            val map: MutableMap<String, Any> = mutableMapOf()
            map["message"] = "Bad credentials"
            map["status"] = false
            return ResponseEntity(map, HttpStatus.UNAUTHORIZED)
        }

        SecurityContextHolder.getContext().authentication = authentication
        val userDetails: UserDetailsImpl = authentication.principal as UserDetailsImpl
        val jwtToken = jwtUtils.generateTokenFromUsername(userDetails)
        val roles: List<String> = userDetails.authorities.stream()
            .map { item -> item.authority.orEmpty() }
            .collect(Collectors.toList())

        val response = UserInfoResponse(id = userDetails.id, jwtToken, userDetails.username, roles)

        return ResponseEntity.ok(response)
    }
}