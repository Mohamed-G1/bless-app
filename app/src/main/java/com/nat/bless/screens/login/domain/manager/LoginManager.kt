package com.nat.bless.screens.login.domain.manager

import com.nat.bless.screens.login.domain.models.LoginResponse

interface LoginManager {
    suspend fun saveUser(response: LoginResponse)
    suspend fun clearUser()


    suspend fun saveRouteId(routeId: Int)

}