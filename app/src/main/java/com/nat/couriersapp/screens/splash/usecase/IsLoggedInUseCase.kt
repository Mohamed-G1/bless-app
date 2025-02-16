package com.nat.couriersapp.screens.splash.usecase

import com.nat.couriersapp.screens.splash.manager.SplashManager
import kotlinx.coroutines.flow.Flow

class IsLoggedInUseCase(
    private val splashManager: SplashManager
) {

    // by adding operator to the function ... we are able to call the function by class name ...
    operator fun invoke(): Flow<Boolean> {
        return splashManager.isLoggedIn()
    }
}