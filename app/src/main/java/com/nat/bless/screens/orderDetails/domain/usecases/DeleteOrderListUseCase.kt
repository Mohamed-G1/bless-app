package com.nat.bless.screens.orderDetails.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.screens.editableConfirmOrder.domain.models.DeleteOrderRequest
import com.nat.bless.screens.orderDetails.domain.repository.OrderDetailsRepository

class DeleteOrderListUseCase(
    private val repository: OrderDetailsRepository
) {

    suspend operator fun invoke(request: BaseRequest<DeleteOrderRequest>) =
        repository.deleteOrderList(request)
}