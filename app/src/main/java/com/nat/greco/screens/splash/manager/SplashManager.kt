package com.nat.greco.screens.splash.manager

import kotlinx.coroutines.flow.Flow

interface SplashManager {
    fun isLoggedIn(): Flow<Boolean>
}