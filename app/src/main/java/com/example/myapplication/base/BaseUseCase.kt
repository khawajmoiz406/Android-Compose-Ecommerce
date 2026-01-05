package com.example.myapplication.base

import kotlinx.coroutines.flow.Flow

interface BaseUseCase<out T, in P> {
    suspend fun invoke(params: P): Flow<Result<T>>
}