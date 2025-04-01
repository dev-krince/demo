package com.pcn.demo.domain.user.repository

import com.pcn.demo.domain.user.entity.LoginInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LoginInfoRepository : JpaRepository<LoginInfo, Long> {
    fun countByIsActiveUser(activeUser: Boolean): Long
}
