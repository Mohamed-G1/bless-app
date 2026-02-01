package com.nat.bless.screens.stocks.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.addNewOrders.models.StockListData
import com.nat.bless.screens.stocks.domain.repository.StocksRepository
import com.nat.bless.screens.stocks.models.SearchRequest
import kotlinx.coroutines.flow.Flow

class SearchStockUseCase(
    private val repository: StocksRepository
) {
    suspend fun invoke(request: BaseRequest<SearchRequest>): Flow<Resource<BaseResponse<List<StockListData>>>> {
        return repository.searchWithStocks(request)
    }
}