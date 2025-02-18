package com.nat.couriersapp.screens.login.domain.manager

import com.nat.couriersapp.screens.login.domain.models.UserResponse

interface LoginManager {
    suspend fun saveUser(response: UserResponse)
    suspend fun clearUser()
}