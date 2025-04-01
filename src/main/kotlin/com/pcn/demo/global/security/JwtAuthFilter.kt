package com.pcn.demo.global.security

import com.pcn.demo.domain.user.constant.Role
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthFilter(
    private val jwtProvider: JwtProvider,
    private val customUserDetailsService: CustomUserDetailsService,
) : OncePerRequestFilter() {

    companion object {
        private const val TOKEN_TYPE: String = "Bearer "
        private const val HEADER_NAME: String = "Authorization"
    }

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: jakarta.servlet.FilterChain) {
        val token: String? = request.getHeader(HEADER_NAME)

        if (token == null) {
            filterChain.doFilter(request, response)
            return
        }

        if (!token.startsWith(TOKEN_TYPE)) {
            request.setAttribute("exceptionMessage", "토큰 형식을 확인해주세요.")
            filterChain.doFilter(request, response)
            return
        }

        val substringToken = token.substring(TOKEN_TYPE.length)

        try {
            if (!jwtProvider.isValidToken(substringToken)) {
                request.setAttribute("exceptionMessage", "유효하지 않은 토큰입니다.")
                filterChain.doFilter(request, response)
                return
            }
        } catch (exception: ExpiredJwtException) {
            request.setAttribute("exceptionMessage", "만료된 토큰입니다.")
            filterChain.doFilter(request, response)
            return
        }

        val role = jwtProvider.getRole(substringToken)

        if (role == Role.ROLE_USER) {
            val id = jwtProvider.getId(substringToken)
            val userDetails = customUserDetailsService.loadUserById(id)
            val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
            SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
        }

        filterChain.doFilter(request, response)
    }
}