package com.nat.bless.screens.dayDetails.domain.data

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.dayDetails.domain.models.DayDetailsRequest
import com.nat.bless.screens.dayDetails.domain.models.DayDetailsResponse
import kotlinx.coroutines.flow.Flow

interface DayDetailsRepository {
    suspend fun getDayDetails(request: BaseRequest<DayDetailsRequest>): Flow<Resource<DayDetailsResponse>>
    suspend fun endDay(request: BaseRequest<DayDetailsRequest>): Resource<BaseResponse<Any>>
}