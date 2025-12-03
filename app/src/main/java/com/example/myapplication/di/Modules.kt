package com.example.myapplication.di

import com.example.myapplication.ui.auth.login.data.LoginRemoteRepo
import com.example.myapplication.ui.auth.login.data.LoginRemoteRepoImpl
import com.example.myapplication.ui.auth.login.domain.LoginUseCase
import com.example.myapplication.ui.auth.login.presentation.LoginViewModel
import com.example.myapplication.ui.dashboard.home.data.HomeRemoteRepo
import com.example.myapplication.ui.dashboard.home.data.HomeRemoteRepoImpl
import com.example.myapplication.ui.dashboard.home.domain.GetHomeUseCase
import com.example.myapplication.ui.dashboard.home.domain.GetProductsByCategoryUseCase
import com.example.myapplication.ui.dashboard.home.presentation.HomeViewModel
import com.example.myapplication.ui.product_detail.data.ProductDetailRemoteRepo
import com.example.myapplication.ui.product_detail.data.ProductDetailRemoteRepoImpl
import com.example.myapplication.ui.product_detail.domain.GetProductDetailUseCase
import com.example.myapplication.ui.product_detail.presentation.ProductDetailViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { createOkHttpClient() }
    single { createRetrofit(get()) }
    single { createApiService(get()) }
}

val loginModule = module {
    single<LoginRemoteRepo> { LoginRemoteRepoImpl(androidContext(), get()) }
    single { LoginUseCase(get()) }
    viewModel { LoginViewModel(get()) }
}

val homeModule = module {
    single<HomeRemoteRepo> { HomeRemoteRepoImpl(androidContext(), get()) }
    single { GetHomeUseCase(get()) }
    single { GetProductsByCategoryUseCase(get()) }
    viewModel { HomeViewModel(get(), get()) }
}

val productDetailModule = module {
    single<ProductDetailRemoteRepo> { ProductDetailRemoteRepoImpl(androidContext(), get()) }
    single { GetProductDetailUseCase(get()) }
    viewModel { ProductDetailViewModel(get()) }
}