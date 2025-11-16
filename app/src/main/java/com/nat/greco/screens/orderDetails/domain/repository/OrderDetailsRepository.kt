package com.nat.greco.screens.orderDetails.domain.repository

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.orders.domain.models.OrderDetailsRequest
import com.nat.greco.screens.orders.domain.models.OrdersResponse
import kotlinx.coroutines.flow.Flow

interface OrderDetailsRepository {

    suspend fun getOrderDetails(request: BaseRequest<OrderDetailsRequest>): Flow<Resource<BaseResponse<OrdersResponse>>>
}