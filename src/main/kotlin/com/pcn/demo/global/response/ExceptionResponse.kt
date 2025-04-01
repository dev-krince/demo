package com.pcn.demo.global.response

class ExceptionResponse(
    private val responseCode: ExceptionResponseCode,

    val message: String
) : GlobalResponse(responseCode, message) {
    val success: Boolean = responseCode.isSuccess

    val status: String = responseCode.httpStatus

    val code: Int = responseCode.code

    val httpCode: Int = responseCode.httpCode
}