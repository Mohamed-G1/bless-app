package com.nat.greco.screens.receviceStock.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.screens.receviceStock.domain.models.ReceiveStockRequest
import com.nat.greco.screens.receviceStock.domain.repository.ReceiveStockRepository

class GetReceiveStockUseCase(
    private val repository: ReceiveStockRepository
) {
    suspend operator fun invoke(request: BaseRequest<ReceiveStockRequest>) =
        repository.getReceiveStock(request)
}