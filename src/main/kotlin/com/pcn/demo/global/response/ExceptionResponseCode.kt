package com.pcn.demo.global.response

enum class ExceptionResponseCode(
    override val isSuccess: Boolean,
    override val code: Int,
    override val httpCode: Int,
    override val httpStatus: String,
    override val message: String
) : ResponseCode {
    BAD_REQUEST(false, 40000, 400, "Bad Request", "올바르지 않은 요청입니다."),
    INVALID_VALUE(false, 40001, 400, "Invalid Value", "요청 값이 올바르지 않습니다."),
    REQUIRE_VALUE(false, 40003, 400, "Require Value", "필수 입력값이 누락되었습니다."),
    UNAUTHORIZED(false, 40100, 401, "Unauthorized", "인증 정보가 일치하지 않습니다."),
    INVALID_TOKEN(false, 40101, 401, "Invalid Token", "유효하지 않은 토큰입니다."),
    INVALID_PASSWORD(false, 40102, 401, "Invalid Password", "비밀번호를 확인해주세요."),
    EMPTY_TOKEN(false, 40103, 401, "Empty Token", "토큰이 없습니다."),
    EXPIRED_TOKEN(false, 40104, 401, "Expired Token", "만료된 토큰입니다."),
    FORBIDDEN(false, 40300, 403, "Forbidden", "해당 리소스에 접근 권한이 없습니다."),
    NOT_FOUND(false, 40400, 404, "Not Found", "리소스가 존재하지 않습니다."),
    NOT_FOUND_RESOURCE(false, 40401, 404, "Not Found Resource", "리소스가 존재하지 않습니다."),
    DUPLICATE_RESOURCE(false, 40900, 409, "Duplicate Resource", "이미 존재하는 리소스입니다."),
    DUPLICATE_USER(false, 40901, 409, "Duplicate User", "이미 존재하는 회원입니다."),
    UNPROCESSABLE_ENTITY(false, 42200, 422, "Unprocessable Entity", "올바른 요청이지만 현재 서버에서 처리할 수 없습니다."),
    INTERNAL_SERVER_ERROR(false, 50000, 500, "Internal Server Error", "서버 에러입니다. 개발자에게 문의해주세요."),
    IMPLEMENTED(false, 50100, 501, "Implemented", "미구현 API입니다.");
}