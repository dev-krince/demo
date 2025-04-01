package com.pcn.demo.global.response

interface ResponseCode {
    val isSuccess: Boolean
    val code: Int
    val httpCode: Int
    val httpStatus: String
    val message: String
}