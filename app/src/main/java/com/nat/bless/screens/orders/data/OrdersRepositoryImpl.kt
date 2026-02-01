package com.nat.bless.screens.orders.data

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.ApiServices
import com.nat.bless.base.network.Resource
import com.nat.bless.base.network.safeApiCall
import com.nat.bless.screens.orders.domain.models.OrderDetailsRequest
import com.nat.bless.screens.orders.domain.models.OrdersRequest
import com.nat.bless.screens.orders.domain.models.OrdersResponse
import com.nat.bless.screens.orders.domain.models.ReturnsResponse
import com.nat.bless.screens.orders.domain.repository.OrdersRepository
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

    override suspend fun getReturnsList(request: BaseRequest<OrdersRequest>): Flow<Resource<BaseResponse<List<ReturnsResponse>>>> = flow {

        emit(Resource.Loading)
        val result = safeApiCall {
            apiServices.returnsOrderList(request)
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