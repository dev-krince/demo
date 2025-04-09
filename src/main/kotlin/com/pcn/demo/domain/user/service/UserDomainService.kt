package com.pcn.demo.domain.user.service

import com.pcn.demo.domain.model.user.User
import com.pcn.demo.domain.model.user.vo.LoginId
import com.pcn.demo.domain.model.user.vo.Password

interface UserDomainService {
    fun validateDuplicateLoginId(loginId: LoginId)

    fun resistUser(user: User): User

    fun getUserFromLoginId(loginId: LoginId): User

    fun validatePasswordMatch(rawPassword: Password, encodedPassword: Password)

    fun validateRegistUserCount()
}
