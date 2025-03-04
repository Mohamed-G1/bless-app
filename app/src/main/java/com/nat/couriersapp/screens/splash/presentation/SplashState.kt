package com.nat.couriersapp.screens.splash.presentation

import com.nat.couriersapp.base.navigation.Destinations


data class SplashState(
    val destination: Destinations = Destinations.Login,
    val shouldNavigate: Boolean = false,
)

