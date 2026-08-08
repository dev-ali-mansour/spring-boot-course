package dev.alimansour.sbecom.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
        val response = HashMap<String, String>()
        e.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val message = error.defaultMessage.orEmpty()
            response[fieldName] = message
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }
}
