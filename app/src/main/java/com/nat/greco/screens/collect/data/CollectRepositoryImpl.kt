package com.nat.greco.screens.collect.data

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.ApiServices
import com.nat.greco.base.network.Resource
import com.nat.greco.base.network.safeApiCall
import com.nat.greco.screens.collect.domain.models.CollectRequest
import com.nat.greco.screens.collect.domain.models.CollectResponse
import com.nat.greco.screens.collect.domain.models.CreatePaymentRequest
import com.nat.greco.screens.collect.domain.repository.CollectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class CollectRepositoryImpl(
    val apiServices: ApiServices
) : CollectRepository {
    override suspend fun getJournals(request: BaseRequest<CollectRequest>): Flow<Resource<BaseResponse<List<CollectResponse>>>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.getJournals(request)
            }
            emit(result)
        }.catch { e ->
            emit(Resource.Error(e.message.toString()))
        }

    override suspend fun createPayment(request: BaseRequest<CreatePaymentRequest>): Resource<BaseResponse<Any>> {
        return safeApiCall { apiServices.createPayment(request) }
    }
}