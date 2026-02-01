package com.nat.bless.screens.stocks.domain.repository

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.addNewOrders.models.StockListData
import com.nat.bless.screens.stocks.models.SearchRequest
import com.nat.bless.screens.stocks.models.StockRequest
import com.nat.bless.screens.stocks.models.returnsModel.ReturnedListData
import com.nat.bless.screens.stocks.models.returnsModel.ReturnsStockResponse
import kotlinx.coroutines.flow.Flow

interface StocksRepository {
    suspend fun getReturnsStocks(request: BaseRequest<StockRequest>): Flow<Resource<ReturnsStockResponse>>
    suspend fun searchWithStocks(
        request: BaseRequest<SearchRequest>
    ): Flow<Resource<BaseResponse<List<StockListData>>>>

    suspend fun searchWithReturns(
        request: BaseRequest<SearchRequest>
    ):  Flow<Resource<BaseResponse<List<ReturnedListData>>>>
}