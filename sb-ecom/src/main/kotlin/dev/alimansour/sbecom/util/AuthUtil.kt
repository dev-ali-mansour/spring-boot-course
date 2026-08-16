package dev.alimansour.sbecom.util

import dev.alimansour.sbecom.model.User
import dev.alimansour.sbecom.repository.UserRepository
import dev.alimansour.sbecom.security.service.UserDetailsImpl
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class AuthUtil(private val userRepository: UserRepository) {
    fun loggedInEmail(): String {
        return getUserDetailsImpl()?.email ?: loggedInUser().email
    }

    fun loggedInUserId(): Long {
        return getUserDetailsImpl()?.id ?: loggedInUser().id!!
    }

    fun loggedInUser(): User {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw UsernameNotFoundException("No authentication context found")

        return userRepository.findByUsername(authentication.name)
            .orElseThrow {
                UsernameNotFoundException("User Not Found with username: ${authentication.name}")
            }
    }

    private fun getUserDetailsImpl(): UserDetailsImpl? =
        SecurityContextHolder.getContext().authentication?.principal as? UserDetailsImpl
}