package dev.alimansour.securitydemo

import dev.alimansour.securitydemo.jwt.JwtUtils
import dev.alimansour.securitydemo.jwt.LoginRequest
import dev.alimansour.securitydemo.jwt.LoginResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

@RestController
class GreetingsController(
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils
) {
    @GetMapping("/hello")
    fun sayHello(): String {
        return "Hello"
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    fun userEndpoint(): String {
        return "Hello, User!"
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    fun adminEndpoint(): String {
        return "Hello, Admin!"
    }

    @PostMapping("/signin")
    fun authenticateUser(@Validated @RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
        val authentication: Authentication
        try {
            authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    loginRequest.username,
                    loginRequest.password
                )
            )
        } catch (e: AuthenticationException) {
            val map: MutableMap<String, Any> = mutableMapOf()
            map["message"] = "Bad credentials"
            map["status"] = false
            return ResponseEntity(map, HttpStatus.UNAUTHORIZED)
        }

        SecurityContextHolder.getContext().authentication = authentication
        val userDetails: UserDetails = authentication.principal as UserDetails
        val jwtToken = jwtUtils.generateTokenFromUsername(userDetails)
        val roles: List<String> = userDetails.authorities.stream()
            .map { item -> item.authority.orEmpty() }
            .collect(Collectors.toList())

        val response = LoginResponse(jwtToken, userDetails.username, roles)

        return ResponseEntity.ok(response)
    }
}
