package com.siad.stayksa.screens.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siad.stayksa.base.navigation.Destinations
import com.siad.stayksa.features.splash.usecase.SplashUseCases
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel(
    private val splashUseCases: SplashUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(splashDefaultState())
    val state = _state.asStateFlow()

    init {
        determineNavigation()
    }

    // Determine which screen will navigate
    private fun determineNavigation() {
        viewModelScope.launch {
            combine(
                splashUseCases.readOnboarding(),
                splashUseCases.isLoggedIn()
            ) { shouldOpenOnboarding, isLoggedIn ->
                if (isLoggedIn) {
                    // if the user is already logged in
                    _state.update { it.copy(destination = Destinations.Home) }
                } else {
                    if (shouldOpenOnboarding) {
                        // if the user is enter the app for the first time, so show the onBoarding
                        _state.update { it.copy(destination = Destinations.OnBoarding) }
                    } else {
                        // if the user is already see the onBoarding screens before, so skip it
                        _state.update { it.copy(destination = Destinations.SignIn) }
                    }
                }

                delay(2000)
                _state.update { it.copy(shouldNavigate = true) }
            }.collect()
        }
    }
}