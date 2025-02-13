package com.siad.stayksa.screens.onboarding.di

import com.siad.stayksa.screens.onboarding.data.manager.OnboardingManagerImpl
import com.siad.stayksa.screens.onboarding.data.repository.OnBoardingRepositoryImp
import com.siad.stayksa.screens.onboarding.domain.manager.OnboardingManager
import com.siad.stayksa.screens.onboarding.domain.repository.OnBoardingRepository
import com.siad.stayksa.screens.onboarding.domain.usecase.GetOnBoardingDataUseCase
import com.siad.stayksa.screens.onboarding.domain.usecase.OnboardingUseCases
import com.siad.stayksa.screens.onboarding.domain.usecase.SaveOnboardingUseCase
import com.siad.stayksa.screens.onboarding.presentation.OnboardingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onboardingModule = module {

    single<OnboardingManager> { OnboardingManagerImpl(get()) }

    single { SaveOnboardingUseCase(get()) }

    single { GetOnBoardingDataUseCase(get()) }

    single { OnboardingUseCases(get(), get()) }

    single<OnBoardingRepository> {
        OnBoardingRepositoryImp(get())
    }

    viewModel { OnboardingViewModel(get()) }
}
