package com.pcn.demo.global.exception

class UserLimitExceededException(message: String = DEFAULT_MESSAGE) : CustomException(message) {

    companion object {
        private const val DEFAULT_MESSAGE = "가입 가능한 회원 수를 초과했습니다."
    }
}
