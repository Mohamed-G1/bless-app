package com.nat.greco.screens.orders.data

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.ApiServices
import com.nat.greco.base.network.Resource
import com.nat.greco.base.network.safeApiCall
import com.nat.greco.screens.orders.domain.models.OrderDetailsRequest
import com.nat.greco.screens.orders.domain.models.OrdersRequest
import com.nat.greco.screens.orders.domain.models.OrdersResponse
import com.nat.greco.screens.orders.domain.repository.OrdersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class OrdersRepositoryImpl(
    private val apiServices: ApiServices
) : OrdersRepository {
    override suspend fun getOrdersList(request: BaseRequest<OrdersRequest>):
            Flow<Resource<BaseResponse<List<OrdersResponse>>>> = flow {
        emit(Resource.Loading)
        val result = safeApiCall {
            apiServices.getOrdersList(request)
        }
        emit(result)
    }.catch { e ->
        emit(Resource.Error(e.message.toString()))
    }

    override suspend fun confirmOrder(request: BaseRequest<OrderDetailsRequest>): Resource<BaseResponse<Any>> =
        safeApiCall {
            apiServices.confirmOrder(request)
        }
}