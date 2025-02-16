package com.nat.couriersapp.screens.splash.presentation

import com.nat.couriersapp.base.navigation.Destinations


data class SplashState(
    val destination: Destinations,
    val shouldNavigate: Boolean
)

fun splashDefaultState() = SplashState(Destinations.Home, false)
