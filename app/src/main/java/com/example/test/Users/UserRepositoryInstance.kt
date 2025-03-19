package com.example.test.Users

import com.example.test.dataModels.UserResponse
import com.example.test.utils.RetrofitInstance

class UserRepositoryInstance {
    suspend fun getUsers(page: Int): UserResponse {
        return RetrofitInstance.api.getUsers(page)
    }
}