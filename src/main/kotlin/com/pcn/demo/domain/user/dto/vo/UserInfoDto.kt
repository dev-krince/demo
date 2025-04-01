package com.pcn.demo.domain.user.dto.vo

import com.pcn.demo.domain.user.constant.Role
import java.time.LocalDateTime

class UserInfoDto(
    val userId: Long,
    val loginId: String,
    val password: String,
    val name: String,
    val role: Role,
    val masterUserId: Long,
    val isInitLogin: Boolean,
    val loginAttemptCount: Int,
    val isActiveUser: Boolean,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime
)
