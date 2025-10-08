package com.example.myapplication.base

abstract class BaseRequest {
    abstract fun toMap(): HashMap<String, Any>
}