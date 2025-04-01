package com.pcn.demo.global.response


enum class SuccessResponseCode(
    override val isSuccess: Boolean,
    override val code: Int,
    override val httpCode: Int,
    override val httpStatus: String,
    override val message: String
) : ResponseCode {
    OK(true, 20000, 200, "OK", "요청이 성공적으로 처리되었습니다. 요청한 데이터를 반환합니다."),
    CREATED(true, 20100, 201, "Created", "요청이 성공적으로 처리되었습니다. 새로운 리소스가 생성되었습니다."),
    NO_CONTENT(true, 20400, 204, "No Content", "요청이 성공적으로 처리되었습니다. 응답 데이터는 없습니다."),
}
