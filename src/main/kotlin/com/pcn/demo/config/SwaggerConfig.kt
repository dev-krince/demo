package com.pcn.demo.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig(
    private val swaggerExceptionResponseConfig: SwaggerExceptionResponseConfig,
) {

    companion object {
        private const val VERSION = "0.0.1"
        private const val PROJECT_TITLE_NAME = "fiss"
        private const val SWAGGER_DESCRIPTION = "api 목록"
        private const val BEARER_FORMAT = "JWT"
        private const val AUTH_SCHEME_NAME = "BearerAuth"
        private const val AUTH_SCHEME = "bearer"
        private const val AUTHENTICATION_HEADER = "Authorization"
    }

    @Bean
    fun openAPI(): OpenAPI {
        val components: Components = swaggerExceptionResponseConfig.exceptionResponseComponents
        val securityScheme = createSecurityScheme()
        val securityRequirement = createSecurityRequirement()
        val info = Info()
            .version(VERSION)
            .title(PROJECT_TITLE_NAME)
            .description(SWAGGER_DESCRIPTION)

        components.addSecuritySchemes(AUTH_SCHEME_NAME, securityScheme)

        return OpenAPI()
            .components(components)
            .addSecurityItem(securityRequirement)
            .info(info)
    }

    private fun createSecurityScheme(): SecurityScheme {
        return SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme(AUTH_SCHEME)
            .bearerFormat(BEARER_FORMAT)
            .`in`(SecurityScheme.In.HEADER)
            .name(AUTHENTICATION_HEADER)
    }

    private fun createSecurityRequirement(): SecurityRequirement {
        return SecurityRequirement().addList(AUTH_SCHEME_NAME)
    }
}