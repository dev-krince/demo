package com.pcn.demo.domain.user.controller

import com.pcn.demo.domain.model.user.dto.LoginDto
import com.pcn.demo.domain.model.user.dto.SignUpDto
import com.pcn.demo.global.response.ResponseCode
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "회원", description = "회원 관련 API")
abstract class BaseUserController {
    @PostMapping("/signup")
    @Operation(summary = "회원 등록", description = "회원가입을 진행합니다.")
    @ApiResponse(description = "회원 등록 성공", responseCode = "20400", content = [Content(mediaType = "application/json", schema = Schema(implementation = ResponseCode::class))])
    @ApiResponse(description = "올바르지 않은 요청", responseCode = "40000", ref = "#/components/responses/40000")
    @ApiResponse(description = "올바르지 않은 값", responseCode = "40001", ref = "#/components/responses/40001")
    @ApiResponse(description = "필수값 누락", responseCode = "40003", ref = "#/components/responses/40003")
    @ApiResponse(description = "해당 리소스에 접근 권한 없음", responseCode = "40300", ref = "#/components/responses/40300")
    @ApiResponse(description = "중복된 회원정보가 존재함", responseCode = "40901", ref = "#/components/responses/40901")
    @ApiResponse(description = "요청을 올바르지만 서버에서 처리할 수 없음", responseCode = "42200", ref = "#/components/responses/42200")
    @ApiResponse(description = "예상하지 못한 예외 및 서버 에러", responseCode = "50000", ref = "#/components/responses/50000")
    abstract fun signUp(@RequestBody signUpDto: SignUpDto): ResponseEntity<ResponseCode>

    @PostMapping("/login")
    @Operation(summary = "회원 로그인", description = "회원 로그인을 시도합니다.")
    @ApiResponse(description = "회원 로그인 성공", responseCode = "20400", content = [Content(mediaType = "application/json", schema = Schema(implementation = ResponseCode::class))])
    @ApiResponse(description = "올바르지 않은 요청", responseCode = "40000", ref = "#/components/responses/40000")
    @ApiResponse(description = "올바르지 않은 값", responseCode = "40001", ref = "#/components/responses/40001")
    @ApiResponse(description = "필수값 누락", responseCode = "40003", ref = "#/components/responses/40003")
    @ApiResponse(description = "틀린 비밀번호", responseCode = "40102", ref = "#/components/responses/40102")
    @ApiResponse(description = "존재하지 않는 리소스", responseCode = "40401", ref = "#/components/responses/40401")
    @ApiResponse(description = "예상하지 못한 예외 및 서버 에러", responseCode = "50000", ref = "#/components/responses/50000")
    abstract fun login(@RequestBody loginDto: LoginDto): ResponseEntity<ResponseCode>

    @GetMapping("/test")
    @Operation(summary = "테스트", description = "권한 테스트입니다. admin 권한을 테스트합니다.")
    abstract fun test(): ResponseEntity<ResponseCode>
}
