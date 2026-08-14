package dev.alimansour.sbecom.controller

import dev.alimansour.sbecom.model.AppRole
import dev.alimansour.sbecom.model.Role
import dev.alimansour.sbecom.model.User
import dev.alimansour.sbecom.repository.RoleRepository
import dev.alimansour.sbecom.repository.UserRepository
import dev.alimansour.sbecom.security.jwt.JwtUtils
import dev.alimansour.sbecom.security.request.SignInRequest
import dev.alimansour.sbecom.security.request.SignUpRequest
import dev.alimansour.sbecom.security.response.MessageResponse
import dev.alimansour.sbecom.security.response.UserInfoResponse
import dev.alimansour.sbecom.security.service.UserDetailsImpl
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

@RestController
@RequestMapping(value = ["/api/auth"])
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val encoder: PasswordEncoder,
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
        val jwtCookie: ResponseCookie = jwtUtils.generateJwtCookie(userDetails)
        val roles: List<String> = userDetails.authorities.stream()
            .map { item -> item.authority.orEmpty() }
            .collect(Collectors.toList())

        val response = UserInfoResponse(
            id = userDetails.id,
            jwtToken = jwtCookie.value,
            username = userDetails.username,
            roles = roles
        )

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .body(response)
    }

    @PostMapping("/signup")
    fun registerUser(@Validated @RequestBody signUpRequest: SignUpRequest): ResponseEntity<Any> {
        if (userRepository.existsByUsername(signUpRequest.username)) {
            return ResponseEntity
                .badRequest()
                .body(MessageResponse("Error: username already taken!"))
        }

        if (userRepository.existsByEmail(signUpRequest.email)) {
            return ResponseEntity
                .badRequest()
                .body(MessageResponse("Error: email already taken!"))
        }

        val user = User(
            username = signUpRequest.username,
            email = signUpRequest.email,
            password = encoder.encode(signUpRequest.password)
                ?: throw RuntimeException("Error: password missing!"),
        )

        val roles: MutableSet<Role> = hashSetOf()
        signUpRequest.roles?.let { strRoles ->
            strRoles.forEach { role ->
                when (role) {
                    "admin" -> {
                        val adminRole = roleRepository.findByName(AppRole.ROLE_ADMIN)
                            .orElseThrow { RuntimeException("Error: Roles is not found!") }
                        roles.add(adminRole)
                    }

                    "seller" -> {
                        val sellerRole = roleRepository.findByName(AppRole.ROLE_SELLER)
                            .orElseThrow { RuntimeException("Error: Roles is not found!") }
                        roles.add(sellerRole)
                    }

                    else -> {
                        val userRole = roleRepository.findByName(AppRole.ROLE_USER)
                            .orElseThrow { RuntimeException("Error: Roles is not found!") }
                        roles.add(userRole)
                    }
                }
            }

        } ?: run {
            val userRole = roleRepository.findByName(AppRole.ROLE_USER)
                .orElseThrow { RuntimeException("Error: Roles is not found!") }
            roles.add(userRole)
        }
        user.roles = roles
        userRepository.save(user)

        return ResponseEntity.ok(MessageResponse("User registered successfully"))
    }
}