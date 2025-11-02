package com.nat.greco.screens.stocks.domain.repository

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.addNewOrders.models.NewProductsResponse
import com.nat.greco.screens.stocks.models.SearchRequest
import com.nat.greco.screens.stocks.models.StockRequest
import com.nat.greco.screens.stocks.models.returnsModel.ReturnsStockResponse
import kotlinx.coroutines.flow.Flow

interface StocksRepository {
    suspend fun getReturnsStocks(request: BaseRequest<StockRequest>): Flow<Resource<ReturnsStockResponse>>
    suspend fun searchWithStocks(
        request: BaseRequest<SearchRequest>
    ): Flow<Resource<NewProductsResponse>>

    suspend fun searchWithReturns(
        request: BaseRequest<SearchRequest>
    ): Flow<Resource<ReturnsStockResponse>>
}