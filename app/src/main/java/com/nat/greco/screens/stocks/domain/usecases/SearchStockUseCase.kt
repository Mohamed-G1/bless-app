package com.nat.greco.screens.stocks.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.addNewOrders.models.NewProductsResponse
import com.nat.greco.screens.stocks.domain.repository.StocksRepository
import com.nat.greco.screens.stocks.models.SearchRequest
import kotlinx.coroutines.flow.Flow

class SearchStockUseCase(
    private val repository: StocksRepository
) {
    suspend fun invoke(request: BaseRequest<SearchRequest>): Flow<Resource<NewProductsResponse>> {
        return repository.searchWithStocks(request)
    }
}