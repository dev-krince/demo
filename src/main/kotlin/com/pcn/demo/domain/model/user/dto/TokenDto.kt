package com.pcn.demo.domain.model.user.dto

class TokenDto(
    val accessToken: String,
    val refreshToken: String
) {
    companion object {
        fun of(accessToken: String, refreshToken: String): TokenDto {
            return TokenDto(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }
    }
}