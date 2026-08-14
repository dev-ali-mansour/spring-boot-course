package dev.alimansour.sbecom.security.jwt

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import tools.jackson.databind.ObjectMapper

@Component
class AuthEntryPointJwt : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        logger.error("Unauthorized error: {}", authException.message)
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val body: MutableMap<String, Any> = mutableMapOf()
        body["status"] = HttpServletResponse.SC_UNAUTHORIZED
        body["error"] = "Unauthorized"
        body["message"] = authException.message.orEmpty()
        body["path"] = request.servletPath

        val mapper = ObjectMapper()
        mapper.writeValue(response.outputStream, body)
    }

    private companion object {
        private val logger: Logger = LoggerFactory.getLogger(AuthEntryPointJwt::class.java)
    }
}