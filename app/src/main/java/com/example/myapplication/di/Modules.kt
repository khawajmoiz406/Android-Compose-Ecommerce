package com.example.myapplication.di

import com.example.myapplication.ui.auth.login.data.LoginRepository
import com.example.myapplication.ui.auth.login.data.LoginRepositoryImpl
import com.example.myapplication.ui.auth.login.data.local.LoginLocalRepo
import com.example.myapplication.ui.auth.login.data.local.LoginLocalRepoImpl
import com.example.myapplication.ui.auth.login.data.remote.LoginRemoteRepo
import com.example.myapplication.ui.auth.login.data.remote.LoginRemoteRepoImpl
import com.example.myapplication.ui.auth.login.domain.LoginUseCase
import com.example.myapplication.ui.auth.login.presentation.LoginViewModel
import com.example.myapplication.ui.dashboard.home.data.HomeRepository
import com.example.myapplication.ui.dashboard.home.data.HomeRepositoryImpl
import com.example.myapplication.ui.dashboard.home.data.local.HomeLocalRepo
import com.example.myapplication.ui.dashboard.home.data.local.HomeLocalRepoImpl
import com.example.myapplication.ui.dashboard.home.data.remote.HomeRemoteRepo
import com.example.myapplication.ui.dashboard.home.data.remote.HomeRemoteRepoImpl
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

val localModule = module {
    single { createRoomDatabase(androidContext()) }
    single { createEncryptedSharedPref(androidContext()) }
}

val networkModule = module {
    single { createOkHttpClient() }
    single { createRetrofit(get()) }
    single { createApiService(get()) }
}

val loginModule = module {
    single<LoginLocalRepo> { LoginLocalRepoImpl(get()) }
    single<LoginRemoteRepo> { LoginRemoteRepoImpl(androidContext(), get()) }
    single<LoginRepository> { LoginRepositoryImpl(get(), get()) }
    single { LoginUseCase(get()) }
    viewModel { LoginViewModel(get()) }
}

val homeModule = module {
    single<HomeLocalRepo> { HomeLocalRepoImpl(get()) }
    single<HomeRemoteRepo> { HomeRemoteRepoImpl(androidContext(), get()) }
    single<HomeRepository> { HomeRepositoryImpl(get(), get()) }
    single { GetHomeUseCase(get()) }
    single { GetProductsByCategoryUseCase(get()) }
    viewModel { HomeViewModel(get(), get()) }
}

val productDetailModule = module {
    single<ProductDetailRemoteRepo> { ProductDetailRemoteRepoImpl(androidContext(), get()) }
    single { GetProductDetailUseCase(get()) }
    viewModel { ProductDetailViewModel(get()) }
}