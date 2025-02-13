package com.siad.stayksa.screens.onboarding.presentation

/**
 * As the app is working with MVI architecture pattern, so this event class to pass the user inputs
 * to the view model by the user
 * */

sealed class OnBoardingEvent {
    data object SkipEvent : OnBoardingEvent()
    data object SignInEvent : OnBoardingEvent()
    data object ExploreEvent : OnBoardingEvent()
}