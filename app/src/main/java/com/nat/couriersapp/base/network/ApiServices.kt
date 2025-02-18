package com.nat.couriersapp.base.network

import com.nat.couriersapp.screens.login.domain.models.LoginRequest
import com.nat.couriersapp.screens.login.domain.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {
    @POST("api/Account/login")
    suspend fun userLogin(
        @Body loginRequest: LoginRequest
    ): Response<UserResponse>
}