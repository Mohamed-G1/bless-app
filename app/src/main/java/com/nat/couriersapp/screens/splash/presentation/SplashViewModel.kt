package com.nat.couriersapp.screens.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nat.couriersapp.base.navigation.Destinations
import com.nat.couriersapp.screens.splash.usecase.SplashUseCases
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
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

    private fun determineNavigation() {
        viewModelScope.launch {
            splashUseCases.isLoggedIn().onEach { isLoggedIn ->
                if (isLoggedIn) {
                    // if the user is already logged in
                    _state.update { it.copy(destination = Destinations.Home) }
                } else {
                    _state.update { it.copy(destination = Destinations.SignIn) }
                }
                delay(2000)
                _state.update { it.copy(shouldNavigate = true) }
            }.collect()
        }
    }
}