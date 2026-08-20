package com.project.app.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MovieNotFoundException::class)
    fun handleMovieNotFound(ex: MovieNotFoundException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error)
    }

    @ExceptionHandler(DuplicateRatingException::class)
    fun handleDuplicateRating(ex: DuplicateRatingException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(HttpStatus.CONFLICT.value(), ex.message)
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error)
    }

    @ExceptionHandler(MovieAlreadyExistsException::class)
    fun handleMovieAlreadyExists(ex: MovieAlreadyExistsException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(HttpStatus.CONFLICT.value(), ex.message)
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationErrors(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val defaultMessage = ex.bindingResult.fieldError?.defaultMessage ?: "Validation Failed"
        val error = ErrorResponse(HttpStatus.BAD_REQUEST.value(), defaultMessage)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error)
    }
}
