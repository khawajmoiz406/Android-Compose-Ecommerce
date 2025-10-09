package com.example.myapplication.core.remote

import com.example.myapplication.models.response.User
import com.example.myapplication.models.response.category.Category
import com.example.myapplication.models.response.product.ProductsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface ApiService {
    @POST(EndPoints.LOGIN)
    suspend fun login(@HeaderMap headers: HashMap<String, String>, @Body body: HashMap<String, Any>): Response<User>

    @GET(EndPoints.PRODUCTS)
    suspend fun getProducts(): Response<ProductsResponse?>

    @GET(EndPoints.CATEGORIES)
    suspend fun getCategories(): Response<List<Category>?>
}