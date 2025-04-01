package com.pcn.demo.global.exception

import com.pcn.demo.global.response.ExceptionResponse
import com.pcn.demo.global.response.ExceptionResponseCode
import com.pcn.demo.global.response.ExceptionResponseCode.*
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.io.IOException

@RestControllerAdvice
class GlobalExceptionHandler {

    companion object {
        private val log = KotlinLogging.logger {}
    }

    //dto 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidExceptionHandler(exception: MethodArgumentNotValidException): ResponseEntity<ExceptionResponse> {
        val exceptionMessage = exception.bindingResult
            .fieldErrors
            .joinToString(", ") {
                it.defaultMessage ?: INVALID_VALUE.message
            }
        val exceptionResponse = ExceptionResponse(INVALID_VALUE, exceptionMessage)

        return ResponseEntity.status(exceptionResponse.httpCode).body(exceptionResponse)
    }

    //존재하지 않는 회원
    @ExceptionHandler(UserNotFoundException::class)
    fun userNotFoundExceptionHandler(exception: UserNotFoundException): ResponseEntity<ExceptionResponse> {
        return generateExceptionResponse(exception, NOT_FOUND_RESOURCE)
    }

    //틀린 비밀번호
    @ExceptionHandler(BadCredentialsException::class)
    fun badCredentialsExceptionHandler(exception: BadCredentialsException): ResponseEntity<ExceptionResponse> {
        return generateExceptionResponse(exception, INVALID_PASSWORD)
    }

    //중복된 리소스
    @ExceptionHandler(com.pcn.demo.global.exception.DuplicateResourceException::class)
    fun duplicateResourceExceptionHandler(exception: com.pcn.demo.global.exception.DuplicateResourceException): ResponseEntity<ExceptionResponse> {
        return generateExceptionResponse(exception, DUPLICATE_RESOURCE)
    }

    //리소스 접근 권한 없음
    @ExceptionHandler(AccessDeniedException::class)
    fun accessDeniedExceptionHandler(exception: AccessDeniedException): ResponseEntity<ExceptionResponse> {
        return generateExceptionResponse(exception, FORBIDDEN)
    }

    //예상 못한 IO 예외
    @ExceptionHandler(IOException::class)
    fun iOExceptionHandler(exception: IOException): ResponseEntity<ExceptionResponse> {
        return generateExceptionResponse(exception, INTERNAL_SERVER_ERROR)
    }

    //예상 못한 일반 예외
    @ExceptionHandler(Exception::class)
    fun exceptionHandler(exception: Exception): ResponseEntity<ExceptionResponse> {
        return generateExceptionResponse(exception, INTERNAL_SERVER_ERROR)
    }

    private fun generateExceptionResponse(
        exception: Exception,
        status: ExceptionResponseCode
    ): ResponseEntity<ExceptionResponse> {
        printExceptionInfo(exception)

        val exceptionMessage = exception.message ?: status.message
        val exceptionResponse = ExceptionResponse(status, exceptionMessage)

        return ResponseEntity.status(status.httpCode).body(exceptionResponse)
    }

    private fun printExceptionInfo(exception: Exception) {
        log.error { "Exception occurred: ${exception.javaClass.simpleName} - ${exception.message}" }
        exception.stackTrace
            .forEach { element -> log.error("at ${element.className}.${element.methodName}(${element.fileName}:${element.lineNumber})") }
    }
}