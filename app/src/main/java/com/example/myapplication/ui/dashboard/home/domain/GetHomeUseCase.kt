package com.example.myapplication.ui.dashboard.home.domain

import com.example.myapplication.base.BaseUseCase
import com.example.myapplication.models.response.HomeResponse
import com.example.myapplication.ui.dashboard.home.data.HomeRepository
import kotlinx.coroutines.flow.Flow

class GetHomeUseCase(private val repo: HomeRepository) : BaseUseCase<HomeResponse?, Unit> {
    override suspend fun invoke(params: Unit): Flow<Result<HomeResponse?>> {
        return repo.getHome()
    }
}