package com.pcn.demo.domain.model.user

import com.pcn.demo.domain.model.user.vo.Role
import com.pcn.demo.domain.model.user.vo.LoginId
import com.pcn.demo.domain.model.user.vo.Password
import com.pcn.demo.domain.model.user.vo.Username
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User private constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,

    @field:Embedded
    val loginId: LoginId,

    @field:Embedded
    val password: Password,

    @field:Embedded
    val username: Username,

    @Column(columnDefinition = "varchar(255)", nullable = false)
    @Enumerated(EnumType.STRING)
    val role: Role,

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    val createdDate: LocalDateTime? = null,

    @Column(nullable = false)
    @UpdateTimestamp
    val modifiedDate: LocalDateTime? = null,
) {

    companion object {
        fun of(
            loginId: LoginId,
            password: Password,
            username: Username,
            role: Role
        ): User {
            return User(
                loginId = loginId,
                password = password,
                username = username,
                role = role,
            )
        }

        fun of(
            id: Long,
            loginId: LoginId,
            password: Password,
            username: Username,
            role: Role,
            createdDate: LocalDateTime,
            modifiedDate: LocalDateTime
        ): User {
            return User(
                id = id,
                loginId = loginId,
                password = password,
                username = username,
                role = role,
                createdDate = createdDate,
                modifiedDate = modifiedDate
            )
        }
    }

    val identifier: Long get() = requireNotNull(id) { "ID must not be null" }
}
