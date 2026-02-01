package com.nat.bless.screens.stocks.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.screens.stocks.domain.repository.StocksRepository
import com.nat.bless.screens.stocks.models.StockRequest

class GetReturnsStockUseCase(
    private val repository: StocksRepository
) {

    suspend fun invoke(request: BaseRequest<StockRequest>) =
        repository.getReturnsStocks(request)

}