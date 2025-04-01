package com.pcn.demo.domain.user.repository

import com.pcn.demo.domain.user.dto.vo.UserInfoDto
import com.pcn.demo.domain.user.entity.User
import org.springframework.stereotype.Repository

@Repository
interface UserQueryRepository {
    fun findByUsername(name: String): User?

    fun searchUser(id: Long, loginId: String, name: String): List<User>

    fun findUserInfoByUserId(id: Long): UserInfoDto?
}
