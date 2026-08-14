package dev.alimansour.sbecom.security.jwt

import dev.alimansour.sbecom.security.service.UserDetailsImpl
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component
import org.springframework.web.util.WebUtils
import java.security.Key
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtils(
    @Value("\${spring.app.jwt.secret}")
    private val jwtSecret: String,

    @Value("\${spring.app.jwt.expirationMs}")
    private val jwtExpirationMs: Long,

    @Value("\${spring.app.jwt.cookieName}")
    private val jwtCookie: String
) {

    @Deprecated("Use getJwtFromCookies() instead")
    fun getJwtFromHeader(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        logger.debug("Authorization Header: {}", bearerToken)
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7) // Remove Bearer Prefix
        }
        return null
    }

    fun getJwtFromCookies(request: HttpServletRequest): String? {
        val cookie = WebUtils.getCookie(request, jwtCookie) ?: return null
        return cookie.value
    }

    fun generateJwtCookie(userDetails: UserDetailsImpl): ResponseCookie {
        val jwt = generateTokenFromUsername(userDetails.username)
        return ResponseCookie.from(jwtCookie, jwt)
            .path("/api")
            .maxAge(24 * 60 * 60)
            .httpOnly(false)
            .build()
    }


    fun getCleanJwtCookie(): ResponseCookie {
        return ResponseCookie.from(jwtCookie, null)
            .path("/api")
            .build()
    }

    fun generateTokenFromUsername(username: String): String {
        return Jwts
            .builder()
            .subject(username)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(key())
            .compact()
    }

    fun getUsernameFromJwtToken(token: String): String =
        Jwts.parser()
            .verifyWith(key() as SecretKey)
            .build()
            .parseSignedClaims(token)
            .payload
            .subject

    fun key(): Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))

    fun validateJwtToken(authToken: String): Boolean {
        try {
            println("Validate")
            Jwts.parser()
                .verifyWith(key() as SecretKey)
                .build()
                .parseSignedClaims(authToken)

            return true
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token: {}", e.message)
        } catch (e: ExpiredJwtException) {
            logger.error("JWT token is expired: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            logger.error("JWT token is unsupported: {}", e.message)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claim string is empty: {}", e.message)
        }
        return false
    }

    private companion object {
        private val logger: Logger = LoggerFactory.getLogger(JwtUtils::class.java)
    }
}
