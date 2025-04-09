package com.pcn.demo.global.security

import com.pcn.demo.domain.model.user.User
import com.pcn.demo.domain.model.user.vo.Username
import com.pcn.demo.domain.user.repository.UserRepository
import com.pcn.demo.global.response.ExceptionResponseCode.*
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

@org.springframework.stereotype.Service
@jakarta.transaction.Transactional
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails? {
        val user = userRepository.findByUsername(Username.of(username)) ?: throw UsernameNotFoundException(NOT_FOUND_RESOURCE.message)

        return CustomUserDetails(id = user.identifier, role = user.role)
    }

    fun loadUserById(id: Long): CustomUserDetails {
        val user: User = userRepository.findById(id)
            .orElseThrow { UsernameNotFoundException(NOT_FOUND_RESOURCE.message) }

        return CustomUserDetails(id = user.identifier, role = user.role)
    }
}
