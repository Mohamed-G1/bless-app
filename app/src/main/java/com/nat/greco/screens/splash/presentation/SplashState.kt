package com.nat.greco.screens.splash.presentation

import com.nat.greco.base.navigation.Destinations


data class SplashState(
    val destination: Destinations = Destinations.Login,
    val shouldNavigate: Boolean = false,
)

