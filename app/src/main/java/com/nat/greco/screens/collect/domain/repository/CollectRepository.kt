package com.nat.greco.screens.collect.domain.repository

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.collect.domain.models.CollectRequest
import com.nat.greco.screens.collect.domain.models.CollectResponse
import com.nat.greco.screens.collect.domain.models.CreatePaymentRequest
import kotlinx.coroutines.flow.Flow

interface CollectRepository {
    suspend fun getJournals(
        request: BaseRequest<CollectRequest>
    ): Flow<Resource<BaseResponse<List<CollectResponse>>>>


    suspend fun createPayment(
        request: BaseRequest<CreatePaymentRequest>
    ): Resource<BaseResponse<Any>>
}