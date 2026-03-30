package com.ecommerce.shoppy.di

import android.content.Context
import com.ecommerce.shoppy.BuildConfig
import com.ecommerce.shoppy.core.pref.EncryptedSharedPref
import com.ecommerce.shoppy.core.local.AppDatabase
import com.ecommerce.shoppy.core.remote.ApiService
import com.ecommerce.shoppy.core.remote.EndPoints
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun createApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

fun createRoomDatabase(context: Context): AppDatabase = AppDatabase.getInstance(context)

fun createEncryptedSharedPref(context: Context): EncryptedSharedPref = EncryptedSharedPref.getInstance(context)

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor.Level.BODY
    } else {
        HttpLoggingInterceptor.Level.NONE
    }
    return OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(EndPoints.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}