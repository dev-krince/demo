package com.pcn.demo.domain.user.repository

import com.pcn.demo.domain.model.user.dto.UserInfoDto
import com.pcn.demo.domain.model.user.User
import com.pcn.demo.domain.model.user.vo.LoginId
import com.pcn.demo.domain.model.user.vo.Username
import org.springframework.stereotype.Repository

@Repository
interface UserQueryRepository {
    fun findByUsername(username: Username): User?

    fun searchUser(id: Long, loginId: LoginId, username: Username): List<User>

    fun findUserInfoByUserId(id: Long): UserInfoDto?
}
