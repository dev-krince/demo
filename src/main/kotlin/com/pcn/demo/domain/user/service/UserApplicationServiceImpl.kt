package com.pcn.demo.domain.user.service

import com.pcn.demo.domain.model.user.dto.LoginDto
import com.pcn.demo.domain.model.user.dto.SignUpDto
import com.pcn.demo.domain.model.user.dto.TokenDto
import com.pcn.demo.domain.model.user.User
import com.pcn.demo.domain.model.user.vo.LoginId
import com.pcn.demo.domain.model.user.vo.Password
import com.pcn.demo.domain.model.user.vo.Role
import com.pcn.demo.domain.model.user.vo.Username
import com.pcn.demo.global.security.JwtProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserApplicationServiceImpl(
    private val userDomainService: UserDomainService,

    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider,
) : UserApplicationService {

    /**
     * 회원가입을 진행합니다.
     * @param signUpDto .
     * @implNote 평문 비밀번호 인코딩 -> 회원 엔티티 객체 생성 -> 최대 가입 수 검증 -> 중복 회원 검증 -> 회원 엔티티 객체 저장
     * @author 25.02.01 김동현
     */
    @Transactional
    override fun signUp(signUpDto: SignUpDto) {
        val username: Username = Username.of(signUpDto.name)
        val loginId: LoginId = LoginId.of(signUpDto.loginId)
        val role: Role = signUpDto.role
        val encodedPassword: String = passwordEncoder.encode(signUpDto.password)
        val password: Password = Password.of(encodedPassword)
        val user = User.of(
            loginId = loginId,
            password = password,
            username = username,
            role = role
        )

        userDomainService.validateRegistUserCount()
        userDomainService.validateDuplicateLoginId(loginId)
        userDomainService.resistUser(user)
    }

    /**
     * 회원 로그인을 진행합니다.
     * 1. 로그인 아이디 기반으로 회원 엔티티 객체 조회
     * 2. dto 의 비밀번호와 조회한 회원 엔티티 객체의 비밀번호 일치 여부 검증
     * 3. 엑세스 토큰, 리프레시 토큰을 담은 TokenDto 생성 및 반환
     * @param loginDto .
     * @return 엑세스 토큰과 리프레시 토큰의 정보를 담은 dto
     * @author 25.02.01 김동현
     */
    override fun login(loginDto: LoginDto): TokenDto {
        val loginId = LoginId.of(loginDto.loginId)
        val user = userDomainService.getUserFromLoginId(loginId)
        val rawPassword: Password = Password.of(loginDto.password)

        userDomainService.validatePasswordMatch(rawPassword, user.password)

        return jwtProvider.generateTokenDto(user.identifier, user.role)
    }
}
