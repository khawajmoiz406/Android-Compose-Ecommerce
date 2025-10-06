package com.example.myapplication.core.remote

import com.example.myapplication.models.response.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST
    suspend fun login(@Body body: Map<String, String>): Response<User>
}