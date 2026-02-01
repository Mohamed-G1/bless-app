package com.nat.bless.screens.collect.data

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.ApiServices
import com.nat.bless.base.network.Resource
import com.nat.bless.base.network.safeApiCall
import com.nat.bless.screens.collect.domain.models.CollectRequest
import com.nat.bless.screens.collect.domain.models.CollectResponse
import com.nat.bless.screens.collect.domain.models.CreatePaymentRequest
import com.nat.bless.screens.collect.domain.models.CustomerSummeryRequest
import com.nat.bless.screens.collect.domain.models.CustomerSummeryResponse
import com.nat.bless.screens.collect.domain.repository.CollectRepository
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

    override suspend fun getCustomerSummery(request: BaseRequest<CustomerSummeryRequest>): Flow<Resource<BaseResponse<CustomerSummeryResponse>>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.getCustomerSummery(request)
            }
            emit(result)
        }.catch { e ->
            emit(Resource.Error(e.message.toString()))
        }


}