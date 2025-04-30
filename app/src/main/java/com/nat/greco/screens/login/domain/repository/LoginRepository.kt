package com.nat.greco.screens.login.domain.repository

import com.nat.greco.base.network.Resource
import com.nat.greco.screens.login.domain.models.LoginRequest
import com.nat.greco.screens.login.domain.models.UserResponse

interface LoginRepository {
    suspend fun userLogin(
        request: LoginRequest
    ): Resource<UserResponse>

}