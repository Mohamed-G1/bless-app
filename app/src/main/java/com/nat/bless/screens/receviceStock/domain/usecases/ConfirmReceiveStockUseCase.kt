package com.nat.bless.screens.receviceStock.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.screens.receviceStock.domain.models.ConfirmReceiveStockRequest
import com.nat.bless.screens.receviceStock.domain.repository.ReceiveStockRepository

class ConfirmReceiveStockUseCase(
    private val repository: ReceiveStockRepository
) {

    suspend operator fun invoke(request: BaseRequest<ConfirmReceiveStockRequest>) =
        repository.confirmReceiveStock(request)
}