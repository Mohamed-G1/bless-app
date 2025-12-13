package com.nat.greco.screens.orders.domain.repository

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.orders.domain.models.OrderDetailsRequest
import com.nat.greco.screens.orders.domain.models.OrdersRequest
import com.nat.greco.screens.orders.domain.models.OrdersResponse
import com.nat.greco.screens.orders.domain.models.ReturnsResponse
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