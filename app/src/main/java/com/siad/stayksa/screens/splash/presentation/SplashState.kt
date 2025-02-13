package com.siad.stayksa.screens.splash.presentation

import com.siad.stayksa.base.navigation.Destinations


data class SplashState(
    val destination: Destinations,
    val shouldNavigate: Boolean
)

fun splashDefaultState() = SplashState(Destinations.OnBoarding, false)
