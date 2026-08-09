package dev.alimansour.sbecom.exception

import dev.alimansour.sbecom.payload.APIResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<APIResponse> {
        val errorsBuilder = StringBuilder()
        e.bindingResult.allErrors.forEach { error ->
            errorsBuilder.appendLine(error.defaultMessage.orEmpty())
        }
        val response = APIResponse(message = errorsBuilder.toString())
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun resourceNotFoundException(e: ResourceNotFoundException): ResponseEntity<APIResponse> {
        val response = APIResponse(message = e.message.orEmpty())
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(APIException::class)
    fun apiException(e: APIException): ResponseEntity<APIResponse> {
        val response = APIResponse(message = e.message.orEmpty())
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Throwable::class)
    fun anyOtherThrowable(e: Throwable): ResponseEntity<APIResponse> {
        val response = APIResponse(message = "Please contact the server administrator!", status = false)
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
