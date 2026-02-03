package com.example.myapplication.ui.home.domain.usecase

import com.example.myapplication.base.FlowUseCase
import com.example.myapplication.core.shared.data.remote.dto.ProductsRequest
import com.example.myapplication.ui.home.data.remote.dto.HomeResponse
import com.example.myapplication.ui.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class GetHomeUseCase(private val repo: HomeRepository) : FlowUseCase<Result<HomeResponse?>, ProductsRequest> {
    override fun invoke(params: ProductsRequest): Flow<Result<HomeResponse?>> {
        return repo.getHome(params)
    }
}