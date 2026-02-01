package com.nat.bless.screens.stocks.data

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.ApiServices
import com.nat.bless.base.network.Resource
import com.nat.bless.base.network.safeApiCall
import com.nat.bless.screens.addNewOrders.models.StockListData
import com.nat.bless.screens.stocks.domain.repository.StocksRepository
import com.nat.bless.screens.stocks.models.SearchRequest
import com.nat.bless.screens.stocks.models.StockRequest
import com.nat.bless.screens.stocks.models.returnsModel.ReturnedListData
import com.nat.bless.screens.stocks.models.returnsModel.ReturnsStockResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class StocksRepositoryImpl(
    private val apiServices: ApiServices
) : StocksRepository {
    override suspend fun getReturnsStocks(request: BaseRequest<StockRequest>): Flow<Resource<ReturnsStockResponse>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.getReturnsStock(request)
            }
            emit(result)
        }.catch { e ->
            emit(Resource.Error(e.message.toString()))
        }

    override suspend fun searchWithStocks(request: BaseRequest<SearchRequest>): Flow<Resource<BaseResponse<List<StockListData>>>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.searchWithStock(request)
            }
            emit(result)
        }.catch { e ->
            emit(Resource.Error(e.message.toString()))
        }

    override suspend fun searchWithReturns(request: BaseRequest<SearchRequest>): Flow<Resource<BaseResponse<List<ReturnedListData>>>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.searchWithReturns(request)
            }
            emit(result)
        }.catch { e ->
            emit(Resource.Error(e.message.toString()))
        }
}