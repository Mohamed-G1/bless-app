package com.nat.bless.screens.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nat.bless.base.navigation.Destinations
import com.nat.bless.screens.splash.usecase.SplashUseCases
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel(
    private val splashUseCases: SplashUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(SplashState())
    val state = _state.asStateFlow()

    init {
        determineNavigation()
    }

    fun onEvent(event: SplashEvent) {
        when (event) {
            is SplashEvent.Navigate -> {
                determineNavigation()
            }

            is SplashEvent.IsLocationGranted  -> {
                onLocationPermissionGranted()
            }
        }
    }

    private fun determineNavigation() {
        viewModelScope.launch {
            splashUseCases.isLoggedIn().onEach { isLoggedIn ->
                if (isLoggedIn) {
                    // if the user is already logged in
                    _state.update { it.copy(destination = Destinations.Home) }
                } else {
                    _state.update { it.copy(destination = Destinations.Login) }
                }
                delay(2000)
                _state.update { it.copy(shouldNavigate = true) }
            }.collect()
        }
    }

    private fun onLocationPermissionGranted() {
        // Update the state to trigger navigation
        _state.update { it.copy(shouldNavigate = true) }
    }
}