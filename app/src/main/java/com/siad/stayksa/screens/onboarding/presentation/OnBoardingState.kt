package com.siad.stayksa.screens.onboarding.presentation

import com.siad.stayksa.screens.onboarding.domain.models.OnBoardingResponse

data class OnBoardingState(
    val model: List<OnBoardingResponse>?,
    val isLoading : Boolean
)

fun onBoardingDefaultState() = OnBoardingState(
    model = listOf(),
    isLoading = false
)

