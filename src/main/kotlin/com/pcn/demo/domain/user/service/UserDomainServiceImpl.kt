package com.pcn.demo.domain.user.service

import com.pcn.demo.domain.model.user.User
import com.pcn.demo.domain.model.user.vo.LoginId
import com.pcn.demo.domain.model.user.vo.Password
import com.pcn.demo.domain.user.repository.LoginInfoRepository
import com.pcn.demo.domain.user.repository.UserRepository
import com.pcn.demo.global.exception.DuplicateResourceException
import com.pcn.demo.global.exception.UserLimitExceededException
import com.pcn.demo.global.exception.UserNotFoundException
import com.pcn.demo.global.response.ExceptionResponseCode
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserDomainServiceImpl(
    private val userRepository: UserRepository,
    private val loginInfoRepository: LoginInfoRepository,

    private val passwordEncoder: PasswordEncoder,
) : UserDomainService {

    /**
     * 회원 로그인 아이디의 중복 여부를 검증합니다.
     * @param loginId 회원 로그인 아이디
     * @exception DuplicateResourceException 이미 존재하는 로그인 아이디인 경우
     * @author 25.02.01 김동현
     */
    override fun validateDuplicateLoginId(loginId: LoginId) {
        val isDuplicateLoginId: Boolean = userRepository.existsByLoginId(loginId)

        if (isDuplicateLoginId) {
            throw DuplicateResourceException("이미 존재하는 아이디입니다.")
        }
    }

    /**
     * 해당 회원 엔티티 객체를 데이터베이스에 저장합니다.
     * @param user 회원 엔티티 객체(pk와 createdAt, modifiedAt도 포함되지 않음)
     * @return db에 저장된 회원 엔티티 객체(pk와 createdAt, modifiedAt도 포함됨)
     * @author 25.02.01 김동현
     */
    override fun resistUser(user: User): User {
        return userRepository.save(user)
    }

    /**
     * 회원의 로그인 아이디로 db를 조회해 해당 회원의 엔티티 객체를 조회합니다.
     * @param loginId 회원 로그인 아이디
     * @implNote db에 회원 정보가 없다면 Optional 을 반환하지 않고 예외를 반환합니다.
     * @exception UserNotFoundException 해당 회원의 정보가 db에 존재하지 않음
     * @return 해당 회원 로그인 아이디를 가지고 있는 회원 엔티티 객체를 반환합니다.
     * @author 25.02.01 김동현
     */
    override fun getUserFromLoginId(loginId: LoginId): User {
        return userRepository.findByLoginId(loginId) ?: throw UserNotFoundException()
    }

    /**
     * 평문 비밀번호와 인코딩 된 비밀번호가 일치하는지 검증합니다.
     * @param rawPassword 평문 비밀번호
     * @param encodedPassword 인코딩 된 비밀번호
     * @implNote 비밀번호가 일치하지 않으면 예외를 반환함
     * @throws BadCredentialsException 평문 비밀번호와 인코딩 된 비밀번호가 일치하지 않음
     * @author 25.02.01 김동현
     */
    override fun validatePasswordMatch(rawPassword: Password, encodedPassword: Password) {
        val isMatches: Boolean = passwordEncoder.matches(rawPassword.value, encodedPassword.value)

        if (!isMatches) {
            throw BadCredentialsException(ExceptionResponseCode.INVALID_PASSWORD.message)
        }
    }

    /**
     * 현재 가입된 회원의 수와 최대 가입 가능한 회원의 수를 비교해 검증합니다.
     * @implNote 최대 가입자 수를 넘으면 예외를 반환함
     * @throws UserLimitExceededException 현재 활성 상태의 회원이 허용 가입자 수와 같거나 많음
     * @author 25.02.04 김동현
     */
    override fun validateRegistUserCount() {
        val LIMIT_USER_COUNT = 5L
        val activeUserCount: Long = loginInfoRepository.countByIsActiveUser(true)
        val isLimitOver = activeUserCount >= LIMIT_USER_COUNT

        if (isLimitOver) {
            throw UserLimitExceededException()
        }
    }
}
