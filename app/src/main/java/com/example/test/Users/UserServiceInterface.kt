package com.example.test.Users

import com.example.test.dataModels.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserServiceInterface {
    @GET("api/")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("results") results: Int = 20,
        @Query("seed") seed: String = "abc"
    ): UserResponse
}