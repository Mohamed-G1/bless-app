package com.nat.bless.screens.splash.presentation

sealed class SplashEvent {

data object Navigate : SplashEvent()
data object IsLocationGranted : SplashEvent()
}