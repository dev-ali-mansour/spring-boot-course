package dev.alimansour.sbecom.exception

import dev.alimansour.sbecom.payload.APIResponse
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<APIResponse> {
        val response = APIResponse(
            status = false,
            errors = e.bindingResult.allErrors.map { it.defaultMessage.orEmpty() }
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun constraintViolationException(e: ConstraintViolationException): ResponseEntity<APIResponse> {
        val response = APIResponse(
            status = false,
            errors = e.constraintViolations.map { it.message }
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun resourceNotFoundException(e: ResourceNotFoundException): ResponseEntity<APIResponse> {
        val response = APIResponse(errors = listOf(e.message.orEmpty()))
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(APIException::class)
    fun apiException(e: APIException): ResponseEntity<APIResponse> {
        val response = APIResponse(errors = listOf(e.message.orEmpty()))
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Throwable::class)
    fun anyOtherThrowable(e: Throwable): ResponseEntity<APIResponse> {
        val response = APIResponse(errors = listOf(e.message.orEmpty()))
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
