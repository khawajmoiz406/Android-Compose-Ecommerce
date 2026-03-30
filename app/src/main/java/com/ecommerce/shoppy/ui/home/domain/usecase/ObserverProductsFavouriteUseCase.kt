package com.ecommerce.shoppy.ui.home.domain.usecase

import com.ecommerce.shoppy.base.FlowUseCase
import com.ecommerce.shoppy.ui.home.data.local.entity.ProductFavoriteStatus
import com.ecommerce.shoppy.ui.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class ObserverProductsFavouriteUseCase(private val repo: HomeRepository) :
    FlowUseCase<List<ProductFavoriteStatus>?, Unit> {
    override fun invoke(params: Unit): Flow<List<ProductFavoriteStatus>?> {
        return repo.observeFavoriteStatus()
    }
}