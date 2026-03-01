package com.example.myapplication.base


open class BaseResponse(
    val total: Int? = null,
    val skip: Int? = null,
    val limit: Int? = null,
)