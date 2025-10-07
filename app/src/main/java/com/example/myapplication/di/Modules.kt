package com.example.myapplication.di

import com.example.myapplication.ui.auth.login.data.LoginRemoteRepo
import com.example.myapplication.ui.auth.login.data.LoginRemoteRepoImpl
import com.example.myapplication.ui.auth.login.domain.LoginUseCase
import com.example.myapplication.ui.auth.login.presentation.LoginViewModel
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