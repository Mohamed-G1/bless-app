package com.siad.stayksa.screens.onboarding.domain.repository

import com.siad.stayksa.base.network.response.BaseResponse
import com.siad.stayksa.screens.onboarding.domain.models.OnBoardingResponse
import kotlinx.coroutines.flow.Flow

interface OnBoardingRepository {
    suspend fun getOnBoardingData(language : String): Flow<BaseResponse<List<OnBoardingResponse>>>
}