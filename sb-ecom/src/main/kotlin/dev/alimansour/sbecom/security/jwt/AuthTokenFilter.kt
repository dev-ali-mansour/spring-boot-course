package dev.alimansour.sbecom.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class AuthTokenFilter(
    private val jwtUtils: JwtUtils,
    private val userDetailsService: UserDetailsService,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        Companion.logger.debug("AuthTokenFilter called for URI: {}", request.requestURI)

        try {
            val jwt = parseJwt(request)
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                val username = jwtUtils.getUsernameFromJwtToken(jwt)
                val userDetails = userDetailsService.loadUserByUsername(username)
                val authentiaction: UsernamePasswordAuthenticationToken =
                    UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities
                    )
                authentiaction.details = WebAuthenticationDetailsSource()
                    .buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentiaction
                Companion.logger.debug("Roles from JWT: {}", userDetails.authorities)

            }
        } catch (e: Exception) {
            Companion.logger.error("Can not set user authentication: {}", e.message)
        }
        filterChain.doFilter(request, response)
    }

    private fun parseJwt(request: HttpServletRequest): String? {
        val jwt = jwtUtils.getJwtFromHeader(request)
        Companion.logger.debug("AuthTokenFilter.kt: {}", jwt)
        return jwt
    }

    private companion object {
        private val logger: Logger = LoggerFactory.getLogger(AuthTokenFilter::class.java)
    }
}
