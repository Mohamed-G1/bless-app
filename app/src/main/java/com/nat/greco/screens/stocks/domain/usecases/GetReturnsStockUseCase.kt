package com.nat.greco.screens.stocks.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.screens.stocks.domain.repository.StocksRepository
import com.nat.greco.screens.stocks.models.StockRequest

class GetReturnsStockUseCase(
    private val repository: StocksRepository
) {

    suspend fun invoke(request: BaseRequest<StockRequest>) =
        repository.getReturnsStocks(request)

}