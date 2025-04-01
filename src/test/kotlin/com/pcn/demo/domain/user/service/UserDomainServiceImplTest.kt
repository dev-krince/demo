package com.pcn.demo.domain.user.service

import com.pcn.demo.domain.user.constant.Role
import com.pcn.demo.domain.user.entity.LoginInfo
import com.pcn.demo.domain.user.entity.User
import com.pcn.demo.domain.user.repository.FakeLoginInfoRepository
import com.pcn.demo.domain.user.repository.FakeUserRepository
import com.pcn.demo.domain.user.repository.UserRepository
import com.pcn.demo.global.exception.DuplicateResourceException
import com.pcn.demo.global.exception.UserLimitExceededException
import com.pcn.demo.global.exception.UserNotFoundException
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

@Tag("test")
@Tag("unitTest")
@DisplayName("회원 도메인 서비스(UserDomainService)")
internal class UserDomainServiceTest {

    @BeforeEach
    fun beforeEach() {
        userRepository.deleteAll()
        loginInfoRepository.deleteAll()
    }

    @Tag("target")
    @Tag("develop")
    @Nested
    @DisplayName("회원 로그인 아이디 중복 여부 검증")
    internal inner class ValidateDuplicateLoginId {
        @Nested
        @DisplayName("성공")
        internal inner class Success {
            @Test
            @DisplayName("존재하지 않는 로그인 아이디라면 반환값 없이 종료된다.")
            fun success() {
                //given
                val loginId = "test"

                //when, then
                assertDoesNotThrow { userDomainService.validateDuplicateLoginId(loginId) }
            }
        }

        @Nested
        @DisplayName("실패, 예외")
        internal inner class Fail {
            @Test
            @DisplayName("중복된 로그인 아이디라면 예외를 반환한다.")
            fun fail() {
                //given
                val loginId = "test"
                val user = User(
                    loginId = loginId,
                    id = 1,
                    password = "password",
                    name = "name",
                    role = Role.ROLE_ADMIN,
                    createdAt = LocalDateTime.now(),
                    modifiedAt = LocalDateTime.now()
                )
                userRepository.save(user)

                //when, then
                assertThrows(DuplicateResourceException::class.java) {
                    userDomainService.validateDuplicateLoginId(
                        loginId
                    )
                }
            }
        }
    }

    @Tag("target")
    @Tag("develop")
    @Nested
    @DisplayName("회원 로그인 아이디로 회원 조회")
    internal inner class GetUserFromLoginId {
        @Nested
        @DisplayName("성공")
        internal inner class Success {
            @Test
            @DisplayName("존재하는 계정이라면 회원 엔티티 객체를 반환한다.")
            fun success() {
                //given
                val loginId = "test"
                val user = User(
                    loginId = loginId,
                    id = 1,
                    password = "password",
                    name = "name",
                    role = Role.ROLE_ADMIN,
                    createdAt = LocalDateTime.now(),
                    modifiedAt = LocalDateTime.now()
                )
                userRepository.save(user)

                //when
                val findUser = userDomainService.getUserFromLoginId(loginId)

                //then
                assertThat(findUser.loginId).isEqualTo(loginId)
            }
        }

        @Nested
        @DisplayName("실패, 예외")
        internal inner class Fail {
            @Test
            @DisplayName("존재하지 않는 계정이라면 예외를 반환한다.")
            fun fail() {
                //given
                val loginId = "test"

                //when, then
                assertThrows(UserNotFoundException::class.java) { userDomainService.getUserFromLoginId(loginId) }
            }
        }
    }

    @Tag("target")
    @Tag("develop")
    @Nested
    @DisplayName("평문 비밀번호와 인코딩된 비밀번호 일치 검증")
    internal inner class ValidatePasswordMatch {
        @Nested
        @DisplayName("성공")
        internal inner class Success {
            @Test
            @DisplayName("두 비밀번호가 일치한다.")
            fun success() {
                //given
                val rawPassword = "testPassword1!"
                val encodedPassword = passwordEncoder.encode(rawPassword)

                //when, then
                assertDoesNotThrow { userDomainService.validatePasswordMatch(rawPassword, encodedPassword) }
            }
        }

        @Nested
        @DisplayName("실패, 예외")
        internal inner class Fail {
            @Test
            @DisplayName("일치하지 않는 비밀번호는 예외를 반환한다.")
            fun fail() {
                //given
                val rawPassword = "testPassword1!"
                val encodedWrongPassword = passwordEncoder.encode("wrongPassword1!")

                //when, then
                assertThrows(BadCredentialsException::class.java) {
                    userDomainService.validatePasswordMatch(rawPassword, encodedWrongPassword)
                }
            }

            @Test
            @DisplayName("인코딩 하지 않고 서로 같은 비밀번호 매치는 예외를 반환한다.")
            fun notEncodedPassword() {
                //given
                val rawPassword = "testPassword1!"

                //when, then
                assertThrows(BadCredentialsException::class.java) {
                    userDomainService.validatePasswordMatch(rawPassword, rawPassword)
                }
            }
        }
    }

    @Tag("target")
    @Tag("develop")
    @Nested
    @DisplayName("최대 가입자 수 검증")
    internal inner class ValidateRegistUserCount {
        @Nested
        @DisplayName("성공")
        internal inner class Success {
            @Test
            @DisplayName("가입자 수가 허용치 이내라면 통과한다.")
            fun success() {
                //given, when, then
                assertDoesNotThrow { userDomainService.validateRegistUserCount() }
            }
        }

        @Nested
        @DisplayName("실패, 예외")
        internal inner class Fail {
            @Test
            @DisplayName("가입자 수가 허용치와 같거나 넘어서면 예외를 반환한다.")
            fun fail() {
                //given
                for (i in 0..4) {
                    val loginInfo: LoginInfo = LoginInfo(
                        userId = "userId",
                        masterUserId = "masterUserId",
                        isInitLogin = true,
                        loginAttemptCount = 0,
                        isActiveUser = true,
                        createdAt = LocalDateTime.now(),
                        modifiedAt = LocalDateTime.now()
                    )
                    loginInfoRepository.save(loginInfo)
                }

                //when, then
                assertThrows(UserLimitExceededException::class.java) { userDomainService.validateRegistUserCount() }
            }
        }
    }

    companion object {
        private lateinit var userDomainService: UserDomainService

        private lateinit var passwordEncoder: PasswordEncoder

        private lateinit var userRepository: UserRepository
        private lateinit var loginInfoRepository: com.pcn.demo.domain.user.repository.LoginInfoRepository

        @JvmStatic
        @BeforeAll
        fun setUp() {
            userRepository = FakeUserRepository()
            loginInfoRepository = FakeLoginInfoRepository()

            passwordEncoder = BCryptPasswordEncoder()

            userDomainService = UserDomainServiceImpl(userRepository, loginInfoRepository, passwordEncoder)
        }
    }
}