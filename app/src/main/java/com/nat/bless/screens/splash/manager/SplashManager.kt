package com.nat.bless.screens.splash.manager

import kotlinx.coroutines.flow.Flow

interface SplashManager {
    fun isLoggedIn(): Flow<Boolean>
}