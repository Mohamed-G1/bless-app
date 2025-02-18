package com.nat.couriersapp.screens.login.domain.repository

import com.nat.couriersapp.base.network.Resource
import com.nat.couriersapp.screens.login.domain.models.LoginRequest
import com.nat.couriersapp.screens.login.domain.models.UserResponse

interface LoginRepository {
    suspend fun userLogin(
        request: LoginRequest
    ): Resource<UserResponse>

}