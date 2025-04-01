package com.pcn.demo.global.exception

class UserNotFoundException(message: String = DEFAULT_MESSAGE) : CustomException(message) {

    companion object {
        private const val DEFAULT_MESSAGE = "존재하지 않는 회원입니다."
    }
}
