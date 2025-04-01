package com.pcn.demo.util

object PermitAllUrlsUtil {
    private val permitAllUrls = listOf(
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/favicon.ico",
        "/actuator/**",
        "/cpu",
        "/jvm",
        "/jdbc",
        "/error-log"
    )

    private val permitAllGetUrls = listOf<String>()

    private val permitAllPostUrls = listOf(
        "/apis/users/signup",
        "/apis/users/login"
    )

    private val permitAllPutUrls = listOf<String>()

    private val permitAllDeleteUrls = listOf<String>()

    fun getPermitAllUrls(): Array<String> {
        return permitAllUrls.toTypedArray()
    }

    fun getPermitAllGetUrls(): Array<String> {
        return permitAllGetUrls.toTypedArray()
    }

    fun getPermitAllPostUrls(): Array<String> {
        return permitAllPostUrls.toTypedArray()
    }

    fun getPermitAllPutUrls(): Array<String> {
        return permitAllPutUrls.toTypedArray()
    }

    fun getPermitAllDeleteUrls(): Array<String> {
        return permitAllDeleteUrls.toTypedArray()
    }
}