package com.pcn.demo.domain.user.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.pcn.demo.domain.model.user.vo.Role
import com.pcn.demo.domain.model.user.dto.SignUpDto
import com.pcn.demo.domain.user.service.UserApplicationService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@Tag("test")
@Tag("unitTest")
@WebMvcTest(controllers = [UserController::class])
@DisplayName("회원 컨트롤러(UserController)")
internal class UserControllerTest{

    @Autowired lateinit var mockMvc: MockMvc

    @Autowired lateinit var objectMapper: ObjectMapper

    @MockBean lateinit var userApplicationService: UserApplicationService

    @Tag("target")
    @Tag("develop")
    @Nested
    @DisplayName("회원 등록")
    internal inner class SignUp {

        @Nested
        @DisplayName("성공")
        internal inner class Success {

            @Test
            @WithMockUser
            @DisplayName("성공")
            fun success() {
                //given
                val uri = "/apis/users/signup"
                val signUpDto = SignUpDto(
                    loginId = "login123",
                    name = "name",
                    password = "Password1!",
                    role = Role.ROLE_ADMIN
                )
                Mockito.doNothing().`when`(userApplicationService).signUp(signUpDto)

                //when, then
                mockMvc.perform(
                    MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDto))
                        .with(csrf())
                )
                    .andExpect(MockMvcResultMatchers.status().isNoContent())
                    .andDo(MockMvcResultHandlers.print())
            }
        }
    }
}