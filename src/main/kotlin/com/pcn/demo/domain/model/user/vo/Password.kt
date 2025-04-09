package com.pcn.demo.domain.model.user.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Password() {

    @Column(name = "password", nullable = false)
    lateinit var value: String private set

    private constructor(value: String) : this() {
        this.value = value
    }

    companion object {
        fun of(value: String): Password {
            return Password(value = value)
        }
    }
}
