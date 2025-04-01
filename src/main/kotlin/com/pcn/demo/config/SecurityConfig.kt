package com.pcn.demo.config

import com.pcn.demo.global.security.*
import com.pcn.demo.util.PermitAllUrlsUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.*
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.*

import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
class SecurityConfig(
    private val jwtProvider: JwtProvider,
    private val userDetailsService: CustomUserDetailsService,
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint,
    private val accessDeniedHandler: CustomAccessDeniedHandler
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val jwtAuthFilter = JwtAuthFilter(jwtProvider, userDetailsService)

        return http
            .authorizeHttpRequests {
                it.requestMatchers("/apis/users/test").hasRole("ADMIN")
                it.requestMatchers(*PermitAllUrlsUtil.getPermitAllUrls()).permitAll()
                it.requestMatchers(GET, *PermitAllUrlsUtil.getPermitAllGetUrls()).permitAll()
                it.requestMatchers(POST, *PermitAllUrlsUtil.getPermitAllPostUrls()).permitAll()
                it.requestMatchers(PUT, *PermitAllUrlsUtil.getPermitAllPutUrls()).permitAll()
                it.requestMatchers(DELETE, *PermitAllUrlsUtil.getPermitAllDeleteUrls()).permitAll()
                it.anyRequest().authenticated()
            }
            .httpBasic { obj: HttpBasicConfigurer<HttpSecurity> -> obj.disable() }
            .formLogin { obj: FormLoginConfigurer<HttpSecurity> -> obj.disable() }
            .csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
            .cors { cors: CorsConfigurer<HttpSecurity> -> this.corsConfigSetting(cors) }
            .sessionManagement { sessionManagementConfigurer: SessionManagementConfigurer<HttpSecurity> -> this.stateless(sessionManagementConfigurer) }
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling { exceptionHandling: ExceptionHandlingConfigurer<HttpSecurity> -> this.authExceptionHandler(exceptionHandling) }
            .getOrBuild()
    }

    private fun corsConfigSetting(cors: CorsConfigurer<HttpSecurity>) {
        val allowedOrigins = listOf(
            "http://localhost:3000"
        )
        val allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")

        val corsConfigSource = CorsConfigurationSource { request: HttpServletRequest ->
            val corsConfig = CorsConfiguration()
            corsConfig.allowedOrigins = allowedOrigins
            corsConfig.allowedMethods = allowedMethods
            corsConfig.allowedHeaders = listOf("*")
            corsConfig.exposedHeaders = listOf("Authorization", "refresh-token")
            corsConfig
        }

        cors.configurationSource(corsConfigSource)
    }

    private fun stateless(sessionManagementConfigurer: SessionManagementConfigurer<HttpSecurity>) {
        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    private fun authExceptionHandler(exceptionHandling: ExceptionHandlingConfigurer<HttpSecurity>) {
        exceptionHandling
            .authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler(accessDeniedHandler)
    }
}
