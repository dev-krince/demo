package com.pcn.demo.domain.user.controller

import com.pcn.demo.domain.model.user.dto.LoginDto
import com.pcn.demo.domain.model.user.dto.SignUpDto
import com.pcn.demo.domain.model.user.dto.TokenDto
import com.pcn.demo.domain.user.service.UserApplicationService
import com.pcn.demo.global.response.ResponseCode
import com.pcn.demo.global.response.SuccessResponseCode
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/apis/users")
class UserController(
    private val userApplicationService: UserApplicationService
) : BaseUserController() {


    @PostMapping("/signup")
    override fun signUp(@RequestBody @Valid signUpDto: SignUpDto): ResponseEntity<ResponseCode> {
        userApplicationService.signUp(signUpDto)

        return ResponseEntity.status(SuccessResponseCode.NO_CONTENT.httpCode).build()
    }

    @PostMapping("/login")
    override fun login(loginDto: LoginDto): ResponseEntity<ResponseCode> {
        val tokenDto: TokenDto = userApplicationService.login(loginDto)

        return ResponseEntity.status(SuccessResponseCode.NO_CONTENT.httpCode)
            .header("Authorization", tokenDto.accessToken)
            .header("Refresh-Token", tokenDto.refreshToken)
            .build()
    }

    @GetMapping("/test")
    override fun test(): ResponseEntity<ResponseCode> {
        return ResponseEntity.status(SuccessResponseCode.NO_CONTENT.httpCode).build()
    }
}
