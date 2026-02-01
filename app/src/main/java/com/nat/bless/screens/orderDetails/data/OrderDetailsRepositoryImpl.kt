package com.nat.bless.screens.orderDetails.data

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.ApiServices
import com.nat.bless.base.network.Resource
import com.nat.bless.base.network.safeApiCall
import com.nat.bless.screens.editableConfirmOrder.domain.models.DeleteOrderRequest
import com.nat.bless.screens.editableConfirmOrder.domain.models.EditableConfirmOrderRequest
import com.nat.bless.screens.editableConfirmOrder.domain.models.UpdateOrderRequest
import com.nat.bless.screens.orderDetails.domain.repository.OrderDetailsRepository
import com.nat.bless.screens.orders.domain.models.OrderDetailsRequest
import com.nat.bless.screens.orders.domain.models.OrdersResponse
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

    override suspend fun getEditableOrderList(request: BaseRequest<EditableConfirmOrderRequest>): Flow<Resource<BaseResponse<OrdersResponse>>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.getEditableOrdersList(request)
            }
            emit(result)
        }.catch { e ->
            emit(Resource.Error(e.message.toString()))
        }

    override suspend fun updateOrderList(request: BaseRequest<UpdateOrderRequest>): Flow<Resource<BaseResponse<OrdersResponse>>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.updateOrdersList(request)
            }
            emit(result)
        }.catch { e ->
            emit(Resource.Error(e.message.toString()))
        }

    override suspend fun deleteOrderList(request: BaseRequest<DeleteOrderRequest>): Flow<Resource<BaseResponse<OrdersResponse>>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.deleteOrdersList(request)
            }
            emit(result)
        }.catch { e ->
            emit(Resource.Error(e.message.toString()))
        }
}