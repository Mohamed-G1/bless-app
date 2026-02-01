package com.nat.bless.screens.orderDetails.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.screens.editableConfirmOrder.domain.models.EditableConfirmOrderRequest
import com.nat.bless.screens.orderDetails.domain.repository.OrderDetailsRepository

class GetEditableOrderListUseCase(
    private val repository: OrderDetailsRepository
) {

    suspend operator fun invoke(request: BaseRequest<EditableConfirmOrderRequest>) =
        repository.getEditableOrderList(request)
}