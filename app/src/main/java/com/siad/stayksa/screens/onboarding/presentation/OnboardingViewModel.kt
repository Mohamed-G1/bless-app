package com.siad.stayksa.screens.onboarding.presentation

import androidx.lifecycle.viewModelScope
import com.siad.stayksa.base.viewModel.BaseViewModel
import com.siad.stayksa.screens.onboarding.domain.usecase.OnboardingUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class OnboardingViewModel(
    private val onboardingUseCases: OnboardingUseCases
) : BaseViewModel() {

    private val _state = MutableStateFlow(onBoardingDefaultState())
    val state = _state.asStateFlow()

    init {
        getOnBoardingData()
    }

    private fun getOnBoardingData() = viewModelScope.launch {
//        _state.update { it.copy(isLoading = true) }
//        onboardingUseCases.getOnBoardingDataUseCase.invoke("en").onEach { response ->
//            _state.update { it.copy(isLoading = false, model = response.data) }
//        }.handleErrors(
//            onHandlingFinishedCallback = {
//                _state.update { it.copy(isLoading = false) }
//            }).launchIn(viewModelScope)
    }

    fun onEvent(event: OnBoardingEvent) {
        when (event) {
            is OnBoardingEvent.SkipEvent -> {
                saveOnboarding()
            }

            is OnBoardingEvent.SignInEvent -> {
                saveOnboarding()
            }

            is OnBoardingEvent.ExploreEvent -> {
                saveOnboarding()
            }

        }
    }

    private fun saveOnboarding() {
        viewModelScope.launch {
            onboardingUseCases.saveOnboardingUseCase()
        }
    }
}