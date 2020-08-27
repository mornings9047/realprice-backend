package com.yourssu.realprice.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalRestExceptionHandler {
    @ExceptionHandler(value = [ProductAlreadyExistsException::class])
    @ResponseStatus(HttpStatus.CONFLICT)
    fun conflictException(e: Exception): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(value = [ProductNotExistsException::class, RecordNotExistsException::class, PrevRecordNotExistsException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFoundException(e: Exception): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.NOT_FOUND)
    }
}
