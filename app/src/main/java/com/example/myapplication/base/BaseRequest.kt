package com.example.myapplication.base

abstract class BaseRequest {
    abstract fun toMap(): Map<String, String>
}