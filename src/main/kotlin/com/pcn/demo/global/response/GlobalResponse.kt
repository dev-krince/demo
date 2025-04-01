package com.pcn.demo.global.response

import io.swagger.v3.oas.annotations.media.Schema

open class GlobalResponse(
    responseCode: ResponseCode,

    @field:Schema(title = "메시지", description = "메시지")
    private val message: String = responseCode.message
) {
    @Schema(title = "요청 성공 여부", description = "요청 성공 여부", example = "true")
    private val success: Boolean = responseCode.isSuccess

    @Schema(title = "HTTP 상태", description = "HTTP 상태")
    private val status: String = responseCode.httpStatus

    @Schema(title = "코드 번호", description = "코드 번호")
    private val code: Int = responseCode.code

    @Schema(title = "HTTP 코드 번호", description = "HTTP 코드 번호")
    private val httpCode: Int = responseCode.httpCode
}