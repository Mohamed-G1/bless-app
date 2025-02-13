package com.siad.stayksa.screens.splash.manager

import kotlinx.coroutines.flow.Flow

interface SplashManager {
    fun readOnboarding(): Flow<Boolean>
    fun isLoggedIn(): Flow<Boolean>
}