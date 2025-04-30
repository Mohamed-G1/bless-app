package com.nat.greco.screens.login.domain.manager

import com.nat.greco.screens.login.domain.models.UserResponse

interface LoginManager {
    suspend fun saveUser(response: UserResponse)
    suspend fun clearUser()
}