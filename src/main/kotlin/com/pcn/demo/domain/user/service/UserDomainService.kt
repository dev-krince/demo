package com.pcn.demo.domain.user.service

import com.pcn.demo.domain.user.entity.User

interface UserDomainService {
    fun validateDuplicateLoginId(loginId: String)

    fun resistUser(user: User): User

    fun getUserFromLoginId(loginId: String): User

    fun validatePasswordMatch(rawPassword: String, encodedPassword: String)

    fun validateRegistUserCount()
}
