package com.example.myapplication.base

import java.io.Serializable

open class BaseResponse(
    val total: Int? = null,
    val skip: Int? = null,
    val limit: Int? = null,
) : Serializable