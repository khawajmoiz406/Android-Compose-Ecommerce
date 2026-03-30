package com.ecommerce.shoppy.core.remote

import com.ecommerce.shoppy.core.model.User
import com.ecommerce.shoppy.core.model.Category
import com.ecommerce.shoppy.core.model.Product
import com.ecommerce.shoppy.core.model.ProductsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {
    @POST(EndPoints.LOGIN)
    suspend fun login(@HeaderMap headers: HashMap<String, String>, @Body body: HashMap<String, Any>): Response<User>

    @GET("${EndPoints.PRODUCTS}/{path}")
    suspend fun getAllProducts(
        @Path("path", encoded = true) path: String = "",
        @QueryMap map: HashMap<String, Any>? = hashMapOf()
    ): Response<ProductsResponse?>

    @GET(EndPoints.CATEGORIES)
    suspend fun getCategories(): Response<List<Category>?>

    @GET("${EndPoints.PRODUCTS}/{id}")
    suspend fun getProductDetail(@Path("id", encoded = true) id: String = ""): Response<Product?>
}