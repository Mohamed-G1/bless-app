package com.nat.bless.screens.orders.domain.repository

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.orders.domain.models.OrderDetailsRequest
import com.nat.bless.screens.orders.domain.models.OrdersRequest
import com.nat.bless.screens.orders.domain.models.OrdersResponse
import com.nat.bless.screens.orders.domain.models.ReturnsResponse
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {
    suspend fun getOrdersList(
        request: BaseRequest<OrdersRequest>
    ): Flow<Resource<BaseResponse<List<OrdersResponse>>>>

    suspend fun getReturnsList(
        request: BaseRequest<OrdersRequest>
    ): Flow<Resource<BaseResponse<List<ReturnsResponse>>>>

    suspend fun confirmOrder(
        request: BaseRequest<OrderDetailsRequest>
    ) : Resource<BaseResponse<Any>>
}