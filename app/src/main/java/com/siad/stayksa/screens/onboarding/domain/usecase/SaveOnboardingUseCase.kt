package com.siad.stayksa.screens.onboarding.domain.usecase

import com.siad.stayksa.screens.onboarding.domain.manager.OnboardingManager

class SaveOnboardingUseCase(
    private val onboardingManager: OnboardingManager
) {

    suspend operator fun invoke() {
        return onboardingManager.saveOnboarding()
    }
}