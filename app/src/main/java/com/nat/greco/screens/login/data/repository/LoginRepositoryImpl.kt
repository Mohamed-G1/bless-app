package com.nat.greco.screens.login.data.repository

import com.nat.greco.base.network.ApiServices
import com.nat.greco.base.network.Resource
import com.nat.greco.base.network.safeApiCall
import com.nat.greco.screens.login.domain.models.LoginRequest
import com.nat.greco.screens.login.domain.models.UserResponse
import com.nat.greco.screens.login.domain.repository.LoginRepository

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