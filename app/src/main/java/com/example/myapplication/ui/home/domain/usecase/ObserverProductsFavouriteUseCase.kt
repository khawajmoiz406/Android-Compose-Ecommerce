package com.example.myapplication.ui.home.domain.usecase

import com.example.myapplication.base.FlowUseCase
import com.example.myapplication.ui.home.data.local.entity.ProductFavoriteStatus
import com.example.myapplication.ui.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class ObserverProductsFavouriteUseCase(private val repo: HomeRepository) :
    FlowUseCase<List<ProductFavoriteStatus>?, Unit> {
    override fun invoke(params: Unit): Flow<List<ProductFavoriteStatus>?> {
        return repo.observeFavoriteStatus()
    }
}