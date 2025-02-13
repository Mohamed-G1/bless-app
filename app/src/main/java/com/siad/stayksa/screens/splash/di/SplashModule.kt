package com.siad.stayksa.features.splash.di
import com.siad.stayksa.screens.splash.data.manager.SplashManagerImpl
import com.siad.stayksa.screens.splash.manager.SplashManager
import com.siad.stayksa.screens.splash.presentation.SplashViewModel
import com.siad.stayksa.features.splash.usecase.IsLoggedInUseCase
import com.siad.stayksa.features.splash.usecase.ReadOnboardingUseCase
import com.siad.stayksa.features.splash.usecase.SplashUseCases
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {

    single<SplashManager> { SplashManagerImpl(get()) }

    single { ReadOnboardingUseCase(get()) }

    single { IsLoggedInUseCase(get()) }


    single { SplashUseCases(get(), get()) }

    viewModel { SplashViewModel(get()) }
}
