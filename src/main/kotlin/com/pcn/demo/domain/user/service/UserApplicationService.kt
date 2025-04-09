package com.pcn.demo.domain.user.service

import com.pcn.demo.domain.model.user.dto.LoginDto
import com.pcn.demo.domain.model.user.dto.SignUpDto
import com.pcn.demo.domain.model.user.dto.TokenDto

interface UserApplicationService {

    fun signUp(signUpDto: SignUpDto)

    fun login(loginDto: LoginDto): TokenDto
}
