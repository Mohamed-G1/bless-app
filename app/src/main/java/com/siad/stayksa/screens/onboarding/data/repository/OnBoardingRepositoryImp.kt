package com.siad.stayksa.screens.onboarding.data.repository

import com.siad.stayksa.base.network.ApiServices
import com.siad.stayksa.base.network.response.BaseResponse
import com.siad.stayksa.screens.onboarding.domain.models.OnBoardingResponse
import com.siad.stayksa.screens.onboarding.domain.repository.OnBoardingRepository
import kotlinx.coroutines.flow.Flow

class OnBoardingRepositoryImp(
    private val apiService: ApiServices
) : OnBoardingRepository {
    override suspend fun getOnBoardingData(language: String): Flow<BaseResponse<List<OnBoardingResponse>>> {
        TODO("Not yet implemented")
    }


}