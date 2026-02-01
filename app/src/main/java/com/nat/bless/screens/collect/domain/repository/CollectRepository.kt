package com.nat.bless.screens.collect.domain.repository

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.collect.domain.models.CollectRequest
import com.nat.bless.screens.collect.domain.models.CollectResponse
import com.nat.bless.screens.collect.domain.models.CreatePaymentRequest
import com.nat.bless.screens.collect.domain.models.CustomerSummeryRequest
import com.nat.bless.screens.collect.domain.models.CustomerSummeryResponse
import kotlinx.coroutines.flow.Flow

interface CollectRepository {
    suspend fun getJournals(
        request: BaseRequest<CollectRequest>
    ): Flow<Resource<BaseResponse<List<CollectResponse>>>>


    suspend fun createPayment(
        request: BaseRequest<CreatePaymentRequest>
    ): Resource<BaseResponse<Any>>


    suspend fun getCustomerSummery(
        request: BaseRequest<CustomerSummeryRequest>
    ): Flow<Resource<BaseResponse<CustomerSummeryResponse>>>
}