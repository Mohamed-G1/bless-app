package com.nat.greco.screens.dayDetails.data

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.network.ApiServices
import com.nat.greco.base.network.Resource
import com.nat.greco.base.network.safeApiCall
import com.nat.greco.screens.dayDetails.domain.data.DayDetailsRepository
import com.nat.greco.screens.dayDetails.domain.models.DayDetailsRequest
import com.nat.greco.screens.dayDetails.domain.models.DayDetailsResponse
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

    override suspend fun endDay(request: BaseRequest<DayDetailsRequest>): Resource<DayDetailsResponse> =
        safeApiCall {
            apiServices.endDay(request)
        }
}