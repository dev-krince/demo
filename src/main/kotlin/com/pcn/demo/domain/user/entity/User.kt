package com.pcn.demo.domain.user.entity

import com.pcn.demo.domain.user.constant.Role
import com.pcn.demo.domain.user.dto.request.SignUpDto
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,

    @Column(nullable = false, unique = true, updatable = false)
    val loginId: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val name: String,

    @Column(columnDefinition = "varchar(255)", nullable = false)
    @Enumerated(EnumType.STRING)
    val role: Role,

    @Column(nullable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    val createdAt: LocalDateTime? = null,

    @Column(nullable = false)
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    val modifiedAt: LocalDateTime? = null,
) {

    companion object {
        fun of(signUpDto: SignUpDto, encodedPassword: String): User {
            return User(
                loginId = signUpDto.loginId,
                password = encodedPassword,
                name = signUpDto.name,
                role = signUpDto.role,
            )
        }
    }

    val identifier: Long get() = requireNotNull(id) { "ID must not be null" }
}
