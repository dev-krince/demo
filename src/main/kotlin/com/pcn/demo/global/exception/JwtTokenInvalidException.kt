package com.pcn.demo.global.exception

import org.springframework.security.authentication.InsufficientAuthenticationException

class JwtTokenInvalidException(message: String) : InsufficientAuthenticationException(message)
