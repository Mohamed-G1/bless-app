package com.nat.greco.screens.stocks.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.stocks.domain.repository.StocksRepository
import com.nat.greco.screens.stocks.models.SearchRequest
import com.nat.greco.screens.stocks.models.returnsModel.ReturnsStockResponse
import kotlinx.coroutines.flow.Flow

class SearchReturnsUseCase(
    private val repository: StocksRepository
) {
    suspend fun invoke(request: BaseRequest<SearchRequest>): Flow<Resource<ReturnsStockResponse>> {
        return repository.searchWithReturns(request)
    }
}