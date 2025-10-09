package com.example.myapplication.ui.dashboard.home.domain

import com.example.myapplication.base.BaseUseCase
import com.example.myapplication.models.response.HomeResponse
import com.example.myapplication.models.response.category.Category
import com.example.myapplication.ui.dashboard.home.data.HomeRemoteRepo

class GetHomeUseCase(private val remoteRepo: HomeRemoteRepo) : BaseUseCase<HomeResponse?, Unit> {
    override suspend fun invoke(params: Unit): Result<HomeResponse?> {
        return remoteRepo.getHome()
    }
}