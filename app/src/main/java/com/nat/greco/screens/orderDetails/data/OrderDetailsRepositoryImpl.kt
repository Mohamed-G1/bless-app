package com.nat.greco.screens.orderDetails.data

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.ApiServices
import com.nat.greco.base.network.Resource
import com.nat.greco.base.network.safeApiCall
import com.nat.greco.screens.orderDetails.domain.repository.OrderDetailsRepository
import com.nat.greco.screens.orders.domain.models.OrderDetailsRequest
import com.nat.greco.screens.orders.domain.models.OrdersResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class OrderDetailsRepositoryImpl(
    private val apiServices: ApiServices
) : OrderDetailsRepository {
    override suspend fun getOrderDetails(request: BaseRequest<OrderDetailsRequest>): Flow<Resource<BaseResponse<OrdersResponse>>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.getOrdersDetails(request)
            }
            emit(result)
        }.catch { e ->
            emit(Resource.Error(e.message.toString()))
        }
}