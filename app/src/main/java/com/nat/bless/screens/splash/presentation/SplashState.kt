package com.nat.bless.screens.splash.presentation

import com.nat.bless.base.navigation.Destinations


data class SplashState(
    val destination: Destinations = Destinations.Home,
    val shouldNavigate: Boolean = false,
)

