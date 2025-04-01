package com.pcn.demo.global.response

class SuccessResponse<T>(
    private val responseCode: SuccessResponseCode,

    val results: T
) : GlobalResponse(responseCode) {
    val success: Boolean = responseCode.isSuccess

    val status: String = responseCode.httpStatus

    val code: Int = responseCode.code

    val httpCode: Int = responseCode.httpCode
}
