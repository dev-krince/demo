package com.pcn.demo.domain.model.user.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class LoginId() {

    @Column(name = "login_id", nullable = false, unique = true, updatable = false)
    lateinit var value: String private set

    private constructor(value: String) : this() {
        this.value = value
    }

    companion object {
        fun of(value: String): LoginId {
            return LoginId(value = value)
        }
    }
}
