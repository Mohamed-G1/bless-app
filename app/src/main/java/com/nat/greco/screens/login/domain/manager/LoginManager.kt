package com.nat.greco.screens.login.domain.manager

import com.nat.greco.screens.login.domain.models.LoginResponse

interface LoginManager {
    suspend fun saveUser(response: LoginResponse)
    suspend fun clearUser()
}