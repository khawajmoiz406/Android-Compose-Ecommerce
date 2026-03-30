package com.ecommerce.shoppy.base

abstract class BaseRequest {
    abstract fun toMap(): HashMap<String, Any>
}