package com.nat.bless.screens.orderDetails.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.screens.editableConfirmOrder.domain.models.UpdateOrderRequest
import com.nat.bless.screens.orderDetails.domain.repository.OrderDetailsRepository

class UpdateOrderListUseCase(
    private val repository: OrderDetailsRepository
) {

    suspend operator fun invoke(request: BaseRequest<UpdateOrderRequest>) =
        repository.updateOrderList(request)
}