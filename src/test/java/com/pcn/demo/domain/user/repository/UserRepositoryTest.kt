package com.pcn.demo.domain.user.repository

import com.pcn.demo.domain.user.constant.Role
import com.pcn.demo.domain.user.dto.request.SignUpDto
import com.pcn.demo.domain.user.entity.User
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Tag("test")
@Tag("integrationTest")
@Transactional
@SpringBootTest
@DisplayName("회원 레포지토리(UserRepository)")
internal class UserRepositoryTest{

    @Autowired
    lateinit var userRepository: UserRepository

    @Tag("target")
    @Tag("develop")
    @Nested
    @DisplayName("회원 이름으로 조회")
    internal inner class FindByUsername {
        @Nested
        @DisplayName("성공")
        internal inner class Success {
            @Test
            @DisplayName("이름이 정확히 일치하는 회원의 정보를 조회한다.")
            fun success() {
                //given
                val name = "피씨엔"
                val signUpDto = SignUpDto(
                    loginId = "pcn",
                    password = "password",
                    role = Role.ROLE_USER,
                    name = name
                )
                val user = User.of(signUpDto, "testEncodedPassword")
                userRepository.save(user)

                //when
                val findUser: User? = userRepository.findByUsername(name)

                //then
                assertThat(findUser!!.name).isEqualTo(name)
            }

            @Test
            @DisplayName("이름이 정확하게 일치하지 않으면 조회하지 않는다.")
            fun differentUsername() {
                //given
                val name = "피씨엔"
                val differentName = "다른피씨엔"
                val signUpDto = SignUpDto(
                    loginId = "pcn",
                    password = "password",
                    role = Role.ROLE_USER,
                    name = differentName
                )
                val user = User.of(signUpDto, "testEncodedPassword")
                userRepository.save(user)

                //when
                val findUser: User? = userRepository.findByUsername(name)

                //then
                assertThat(findUser).isNull()
            }
        }
    }
}