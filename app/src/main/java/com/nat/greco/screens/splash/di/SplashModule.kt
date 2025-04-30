package com.nat.greco.screens.splash.di
import com.nat.greco.screens.splash.data.manager.SplashManagerImpl
import com.nat.greco.screens.splash.manager.SplashManager
import com.nat.greco.screens.splash.presentation.SplashViewModel
import com.nat.greco.screens.splash.usecase.IsLoggedInUseCase
import com.nat.greco.screens.splash.usecase.SplashUseCases
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {

    single<SplashManager> { SplashManagerImpl(get()) }

    single { IsLoggedInUseCase(get()) }

    single { SplashUseCases(get()) }

    viewModel { SplashViewModel(get()) }
}
