package com.pcn.demo.config

import com.pcn.demo.global.response.ExceptionResponseCode
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.media.Schema
import io.swagger.v3.oas.models.responses.ApiResponse
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerExceptionResponseConfig {
    val exceptionResponseComponents: Components by lazy {
        createComponent(Components())
    }

    private fun createComponent(components: Components): Components {
        for (exceptionResponseCode in ExceptionResponseCode.entries) {
            components.addResponses(
                exceptionResponseCode.code.toString(),
                createApiResponse(generateDescription(exceptionResponseCode), generateExampleJson(exceptionResponseCode))
            )
        }

        return components
    }

    private fun createApiResponse(description: String, example: String): ApiResponse {
        return ApiResponse()
            .description(description)
            .content(
                Content().addMediaType(
                    "application/json",
                    MediaType().schema(Schema<Any>()).example(example)
                )
            )
    }

    private fun generateExampleJson(responseCode: ExceptionResponseCode): String {
        return String.format(
            """{
  "success": %s,
  "status": "%s",
  "code": %d,
  "message": "%s"
}""",
            responseCode.isSuccess,
            responseCode.httpStatus,
            responseCode.code,
            responseCode.message
        )
    }

    private fun generateDescription(responseCode: ExceptionResponseCode): String {
        return when (responseCode) {
            ExceptionResponseCode.BAD_REQUEST -> "올바르지 않은 요청"
            ExceptionResponseCode.INVALID_VALUE -> "올바르지 않은 값"
            ExceptionResponseCode.REQUIRE_VALUE -> "필수값 누락"
            ExceptionResponseCode.UNAUTHORIZED -> "인증 정보 불일치"
            ExceptionResponseCode.INVALID_TOKEN -> "유효하지 않은 토큰"
            ExceptionResponseCode.INVALID_PASSWORD -> "틀린 비밀번호"
            ExceptionResponseCode.EMPTY_TOKEN -> "토큰 누락"
            ExceptionResponseCode.EXPIRED_TOKEN -> "만료된 토큰 사용"
            ExceptionResponseCode.FORBIDDEN -> "해당 리소스에 접근 권한 없음"
            ExceptionResponseCode.NOT_FOUND -> "존재하지 않는 api"
            ExceptionResponseCode.NOT_FOUND_RESOURCE -> "존재하지 않는 리소스"
            ExceptionResponseCode.DUPLICATE_RESOURCE -> "중복된 리소스가 존재함"
            ExceptionResponseCode.DUPLICATE_USER -> "중복된 회원정보가 존재함"
            ExceptionResponseCode.UNPROCESSABLE_ENTITY -> "요청은 올바르지만 서버에서 처리할 수 없음"
            ExceptionResponseCode.INTERNAL_SERVER_ERROR -> "예상하지 못한 예외 및 서버 에러"
            ExceptionResponseCode.IMPLEMENTED -> "미구현 api"
        }
    }
}