package com.pcn.demo.domain.model.user

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.*
import jakarta.persistence.Id
import jakarta.persistence.TemporalType.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@jakarta.persistence.Entity
@jakarta.persistence.Table
@EntityListeners(AuditingEntityListener::class)
class LoginInfo(
    @Id
    @Column
    @GeneratedValue(strategy = IDENTITY)
    private val id: Long? = null,

    @Column(nullable = false)
    val userId: String,

    @Column(nullable = false)
    val masterUserId: String,

    @Column(name = "is_init_login", nullable = false)
    val isInitLogin: Boolean = false,

    @Column(nullable = false)
    val loginAttemptCount: Int = 0,

    @Column(name = "is_active_user", nullable = false)
    val isActiveUser: Boolean = false,

    @Column(nullable = false)
    @CreationTimestamp
    @jakarta.persistence.Temporal(TIMESTAMP)
    val createdAt: java.time.LocalDateTime? = null,

    @Column(nullable = false)
    @UpdateTimestamp
    @jakarta.persistence.Temporal(TIMESTAMP)
    val modifiedAt: java.time.LocalDateTime? = null
) {
    companion object {
        private const val DEFAULT_IS_INIT_LOGIN = true
        private const val DEFAULT_LOGIN_ATTEMPT_COUNT = 0
        private const val DEFAULT_IS_ACTIVE_USER = true

        fun of(userId: String, masterUserId: String): LoginInfo {
            return LoginInfo(
                userId = userId,
                masterUserId = masterUserId,
                isInitLogin = DEFAULT_IS_INIT_LOGIN,
                loginAttemptCount = DEFAULT_LOGIN_ATTEMPT_COUNT,
                isActiveUser = DEFAULT_IS_ACTIVE_USER
            )
        }
    }

    val identifier: Long get() = requireNotNull(id) { "ID must not be null" }
}
