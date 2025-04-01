package com.pcn.demo.global.security

import com.pcn.demo.global.response.ExceptionResponse
import com.pcn.demo.global.response.ExceptionResponseCode
import com.pcn.demo.global.response.ExceptionResponseCode.*
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class CustomAccessDeniedHandler(
    private val objectMapper: com.fasterxml.jackson.databind.ObjectMapper
) : AccessDeniedHandler {

    companion object {
        private const val ENCODING_TYPE = "UTF-8"
    }

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        val status: ExceptionResponseCode = FORBIDDEN
        val exceptionResponse = ExceptionResponse(status, "수정 예정")
        val responseBody = objectMapper.writeValueAsString(exceptionResponse)

        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = status.httpCode
        response.characterEncoding = ENCODING_TYPE
        response.writer.write(responseBody)
    }
}