package com.ecommerce.shoppy.ui.home.domain.usecase

import com.ecommerce.shoppy.base.FlowUseCase
import com.ecommerce.shoppy.core.shared.data.remote.dto.ProductsRequest
import com.ecommerce.shoppy.ui.home.data.remote.dto.HomeResponse
import com.ecommerce.shoppy.ui.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class GetHomeUseCase(private val repo: HomeRepository) : FlowUseCase<Result<HomeResponse?>, ProductsRequest> {
    override fun invoke(params: ProductsRequest): Flow<Result<HomeResponse?>> {
        return repo.getHome(params)
    }
}