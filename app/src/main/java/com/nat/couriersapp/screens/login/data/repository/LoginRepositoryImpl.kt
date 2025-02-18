package com.nat.couriersapp.screens.login.data.repository

import com.nat.couriersapp.base.network.ApiServices
import com.nat.couriersapp.base.network.Resource
import com.nat.couriersapp.base.network.safeApiCall
import com.nat.couriersapp.screens.login.domain.models.LoginRequest
import com.nat.couriersapp.screens.login.domain.models.UserResponse
import com.nat.couriersapp.screens.login.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val apiServices: ApiServices
) : LoginRepository {
    override suspend fun userLogin(
        request: LoginRequest
    ): Resource<UserResponse> {
        return safeApiCall {
            apiServices.userLogin(request)
        }
    }

}