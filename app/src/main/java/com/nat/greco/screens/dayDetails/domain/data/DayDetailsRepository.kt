package com.nat.greco.screens.dayDetails.domain.data

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.dayDetails.domain.models.DayDetailsRequest
import com.nat.greco.screens.dayDetails.domain.models.DayDetailsResponse
import kotlinx.coroutines.flow.Flow

interface DayDetailsRepository {
    suspend fun getDayDetails(request: BaseRequest<DayDetailsRequest>): Flow<Resource<DayDetailsResponse>>
    suspend fun endDay(request: BaseRequest<DayDetailsRequest>): Resource<BaseResponse<Any>>
}