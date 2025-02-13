package com.siad.stayksa.features.splash.usecase

import com.siad.stayksa.screens.splash.manager.SplashManager
import kotlinx.coroutines.flow.Flow

class ReadOnboardingUseCase(
    private val splashManager: SplashManager
) {

    // by adding operator to the function ... we are able to call the function by class name ...
    operator fun invoke(): Flow<Boolean> {
        return splashManager.readOnboarding()
    }
}