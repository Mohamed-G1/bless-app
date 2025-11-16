package com.nat.greco.screens.receviceStock.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.screens.receviceStock.domain.models.ConfirmReceiveStockRequest
import com.nat.greco.screens.receviceStock.domain.repository.ReceiveStockRepository

class ConfirmReceiveStockUseCase(
    private val repository: ReceiveStockRepository
) {

    suspend operator fun invoke(request: BaseRequest<ConfirmReceiveStockRequest>) =
        repository.confirmReceiveStock(request)
}