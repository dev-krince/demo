package com.pcn.demo.domain.user.service

import com.pcn.demo.domain.user.dto.request.LoginDto
import com.pcn.demo.domain.user.dto.request.SignUpDto
import com.pcn.demo.domain.user.dto.response.TokenDto

interface UserApplicationService {

    fun signUp(signUpDto: SignUpDto)

    fun login(loginDto: LoginDto): TokenDto
}
