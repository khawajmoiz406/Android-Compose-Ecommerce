package com.example.myapplication.base

interface BaseUseCase<out T, in P> {
    suspend fun invoke(params: P): Result<T>
}