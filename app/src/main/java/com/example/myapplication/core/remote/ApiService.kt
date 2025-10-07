package com.example.myapplication.core.remote

import com.example.myapplication.models.response.User
import com.example.myapplication.models.response.product.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST(EndPoints.LOGIN)
    suspend fun login(@Body body: Map<String, Any>): Response<User>

    @GET(EndPoints.PRODUCTS)
    suspend fun getProducts(): Response<List<Product>?>
}