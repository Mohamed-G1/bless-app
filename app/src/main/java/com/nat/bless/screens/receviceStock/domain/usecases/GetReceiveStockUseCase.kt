package com.nat.bless.screens.receviceStock.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.screens.receviceStock.domain.models.ReceiveStockRequest
import com.nat.bless.screens.receviceStock.domain.repository.ReceiveStockRepository

class GetReceiveStockUseCase(
    private val repository: ReceiveStockRepository
) {
    suspend operator fun invoke(request: BaseRequest<ReceiveStockRequest>) =
        repository.getReceiveStock(request)
}