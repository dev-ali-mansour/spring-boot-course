package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.model.AppRole
import dev.alimansour.sbecom.model.Role
import dev.alimansour.sbecom.model.User
import dev.alimansour.sbecom.payload.AuthenticationResult
import dev.alimansour.sbecom.repository.RoleRepository
import dev.alimansour.sbecom.repository.UserRepository
import dev.alimansour.sbecom.security.jwt.JwtUtils
import dev.alimansour.sbecom.security.request.SignInRequest
import dev.alimansour.sbecom.security.request.SignUpRequest
import dev.alimansour.sbecom.security.response.UserInfoResponse
import dev.alimansour.sbecom.security.service.UserDetailsImpl
import jakarta.transaction.Transactional
import org.springframework.http.ResponseCookie
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
@Transactional
class AuthServiceImpl(
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val encoder: PasswordEncoder,
) : AuthService {
    override fun login(signInRequest: SignInRequest): AuthenticationResult {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                signInRequest.username,
                signInRequest.password
            )
        )

        SecurityContextHolder.getContext().authentication = authentication
        val userDetails: UserDetailsImpl = authentication.principal as UserDetailsImpl
        val jwtCookie: ResponseCookie = jwtUtils.generateJwtCookie(userDetails)
        val roles: List<String> = userDetails.authorities.stream()
            .map { item -> item.authority.orEmpty() }
            .collect(Collectors.toList())

        val response = UserInfoResponse(
            id = userDetails.id,
            firstName = userDetails.firstName,
            lastName = userDetails.lastName,
            jwtToken = jwtCookie.value,
            username = userDetails.username,
            email = userDetails.email,
            roles = roles
        )

        return AuthenticationResult(
            userInfoResponse = response,
            jwtCookie = jwtCookie
        )
    }

    override fun register(signUpRequest: SignUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.username)) {
            throw RuntimeException("Error: username already taken!")
        }

        if (userRepository.existsByEmail(signUpRequest.email)) {
            throw RuntimeException("Error: email already taken!")
        }

        val user = User(
            firstName = signUpRequest.firstName,
            lastName = signUpRequest.lastName,
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
    }

    override fun getCurrentUsername(): String {
        SecurityContextHolder.getContext().authentication?.let { authentication ->
            return authentication.name.orEmpty()
        }
        throw RuntimeException("Error: User not authenticated!")
    }

    override fun getUserInfo(): UserInfoResponse {
        SecurityContextHolder.getContext().authentication?.let { authentication ->
            if (authentication.principal !is UserDetailsImpl) {
                throw RuntimeException("Error: User not authenticated!")
            }
            val userDetails: UserDetailsImpl = authentication.principal as UserDetailsImpl
            val roles: List<String> = userDetails.authorities.stream()
                .map { item -> item.authority.orEmpty() }
                .collect(Collectors.toList())

            val response = UserInfoResponse(
                id = userDetails.id,
                firstName = userDetails.firstName,
                lastName = userDetails.lastName,
                username = userDetails.username,
                email = userDetails.email,
                roles = roles
            )
            return response
        }
        throw RuntimeException("Error: User not authenticated!")
    }

    override fun logout(): ResponseCookie = jwtUtils.getCleanJwtCookie()
}