package com.pcn.demo.global.security

import com.pcn.demo.global.response.ExceptionResponse
import com.pcn.demo.global.response.ExceptionResponseCode
import com.pcn.demo.global.response.ExceptionResponseCode.*
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint(
    private val objectMapper: com.fasterxml.jackson.databind.ObjectMapper
) : AuthenticationEntryPoint {

    companion object {
        private const val ENCODING_TYPE = "UTF-8"
    }

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val status: ExceptionResponseCode = INVALID_TOKEN
        val exceptionMessage = getExceptionMessage(request)
        val exceptionResponse = ExceptionResponse(status, exceptionMessage)
        val responseBody = objectMapper.writeValueAsString(exceptionResponse)

        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = status.httpCode
        response.characterEncoding = ENCODING_TYPE
        response.writer.write(responseBody)
    }

    private fun getExceptionMessage(
        request: HttpServletRequest
    ): String {
        val requestTransferExceptionMessage = request.getAttribute("exceptionMessage") as String

        return requestTransferExceptionMessage
    }
}
