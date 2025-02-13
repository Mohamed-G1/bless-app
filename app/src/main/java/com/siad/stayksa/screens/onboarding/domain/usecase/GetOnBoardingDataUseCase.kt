package com.siad.stayksa.screens.onboarding.domain.usecase

import com.siad.stayksa.base.network.response.BaseResponse
import com.siad.stayksa.screens.onboarding.domain.models.OnBoardingResponse
import com.siad.stayksa.screens.onboarding.domain.repository.OnBoardingRepository
import kotlinx.coroutines.flow.Flow

class GetOnBoardingDataUseCase(
    private val onBoardingRepository: OnBoardingRepository
) {
    suspend operator fun invoke(language: String): Flow<BaseResponse<List<OnBoardingResponse>>> {
        return onBoardingRepository.getOnBoardingData(language = language)
    }
}

