package com.pcn.demo.domain.user.service

import com.pcn.demo.domain.user.dto.request.LoginDto
import com.pcn.demo.domain.user.dto.request.SignUpDto
import com.pcn.demo.domain.user.dto.response.TokenDto
import com.pcn.demo.domain.user.entity.User
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
        val encodedPassword: String = passwordEncoder.encode(signUpDto.password)
        val user = User.of(signUpDto, encodedPassword)

        userDomainService.validateRegistUserCount()
        userDomainService.validateDuplicateLoginId(signUpDto.loginId)
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
        val user = userDomainService.getUserFromLoginId(loginDto.loginId)

        userDomainService.validatePasswordMatch(loginDto.password, user.password)

        return jwtProvider.generateTokenDto(user.identifier, user.role)
    }
}
