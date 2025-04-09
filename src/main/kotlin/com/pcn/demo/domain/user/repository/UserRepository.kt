package com.pcn.demo.domain.user.repository

import com.pcn.demo.domain.model.user.User
import com.pcn.demo.domain.model.user.vo.LoginId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>, UserQueryRepository {

    fun existsByLoginId(loginId: LoginId): Boolean

    fun findByLoginId(loginId: LoginId): User?
}
