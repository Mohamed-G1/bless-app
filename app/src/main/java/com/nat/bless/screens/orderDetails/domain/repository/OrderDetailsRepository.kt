package com.nat.bless.screens.orderDetails.domain.repository

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.editableConfirmOrder.domain.models.DeleteOrderRequest
import com.nat.bless.screens.editableConfirmOrder.domain.models.EditableConfirmOrderRequest
import com.nat.bless.screens.editableConfirmOrder.domain.models.UpdateOrderRequest
import com.nat.bless.screens.orders.domain.models.OrderDetailsRequest
import com.nat.bless.screens.orders.domain.models.OrdersResponse
import kotlinx.coroutines.flow.Flow

interface OrderDetailsRepository {

    suspend fun getOrderDetails(request: BaseRequest<OrderDetailsRequest>): Flow<Resource<BaseResponse<OrdersResponse>>>
    suspend fun getEditableOrderList(request: BaseRequest<EditableConfirmOrderRequest>): Flow<Resource<BaseResponse<OrdersResponse>>>
    suspend fun updateOrderList(request: BaseRequest<UpdateOrderRequest>): Flow<Resource<BaseResponse<OrdersResponse>>>
    suspend fun deleteOrderList(request: BaseRequest<DeleteOrderRequest>): Flow<Resource<BaseResponse<OrdersResponse>>>
}