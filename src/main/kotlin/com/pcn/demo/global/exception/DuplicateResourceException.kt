package com.pcn.demo.global.exception

class DuplicateResourceException(message: String = DEFAULT_MESSAGE) : CustomException(message) {

    companion object {
        private const val DEFAULT_MESSAGE = "이미 존재하는 리소스입니다."
    }
}