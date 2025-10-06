package com.example.myapplication.base

interface BaseUseCase<out T : Any, in P : BaseRequest> {
    suspend fun invoke(params: P): Result<T>
}