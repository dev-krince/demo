package com.pcn.demo.domain.user.repository

import com.pcn.demo.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>, UserQueryRepository {

    fun existsByLoginId(loginId: String): Boolean

    fun findByLoginId(loginId: String): User?
}
