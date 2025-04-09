package com.pcn.demo.domain.model.user.dto

import com.pcn.demo.domain.model.user.vo.Role
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

@Schema(description = "회원 가입 요청 dto")
class SignUpDto(

    @field:Schema(title = "로그인 아이디", description = "회원 로그인 아이디(6 ~ 15 영어 소문자, 숫자 조합)", required = true, example = "login1")
    @field:NotBlank(message = "아이디를 입력해주세요.")
    @field:Size(min = 6, max = 15, message = "아이디는 6자 이상 15자 이하여야합니다.")
    @field:Pattern(regexp = "^[a-z0-9]*$", message = "영어 소문자와 숫자만 사용할 수 있습니다.")
    val loginId: String,

    @field:Schema(title = "비밀번호", description = "8 ~ 20자 영어 소문자, 대문자, 숫자 조합", required = true, example = "TestPassword1")
    @field:NotBlank(message = "비밀번호를 입력해주세요.")
    @field:Size(min = 8, max = 20, message = "8 ~ 20자 이내여야합니다.")
    @field:Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d!@#\$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/]{8,20}$",
        message = "비밀번호는 영어 대소문자와 숫자를 각각 하나 이상 포함하고, 특수문자를 포함한 8~20자 이내여야 합니다."
    )
    val password: String,

    @field:Schema(title = "회원명", description = "2 ~ 20 한글과 영문 조합", required = true, example = "testNickname1")
    @field:NotBlank(message = "회원명을 입력해주세요.")
    @field:Size(min = 2, max = 20, message = "2 ~ 20자 이내여야합니다.")
    @field:Pattern(regexp = "^[a-zA-Z가-힣0-9]*$", message = "한글, 영문, 숫자만 사용할 수 있습니다.")
    val name: String,

    @field:Schema(title = "권한", description = "해당 회원의 계정 권한", required = true, example = "ROLE_USER")
    @field:NotNull(message = "회원 권한을 입력해주세요.")
    val role: Role,
)
