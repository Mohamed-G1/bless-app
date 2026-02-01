package com.nat.bless.screens.dayDetails.data

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.ApiServices
import com.nat.bless.base.network.Resource
import com.nat.bless.base.network.safeApiCall
import com.nat.bless.screens.dayDetails.domain.data.DayDetailsRepository
import com.nat.bless.screens.dayDetails.domain.models.DayDetailsRequest
import com.nat.bless.screens.dayDetails.domain.models.DayDetailsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class DayDetailsRepositoryImpl(
    private val apiServices: ApiServices
) : DayDetailsRepository {
    override suspend fun getDayDetails(request: BaseRequest<DayDetailsRequest>): Flow<Resource<DayDetailsResponse>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.getDayDetails(request)
            }
            emit(result)
        }.catch { e ->
            emit(Resource.Error(e.message.toString()))
        }

    override suspend fun endDay(request: BaseRequest<DayDetailsRequest>): Resource<BaseResponse<Any>> =
        safeApiCall {
            apiServices.endDay(request)
        }
}