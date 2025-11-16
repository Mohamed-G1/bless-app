package com.nat.greco.screens.stocks.data

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.ApiServices
import com.nat.greco.base.network.Resource
import com.nat.greco.base.network.safeApiCall
import com.nat.greco.screens.addNewOrders.models.NewProductsResponse
import com.nat.greco.screens.addNewOrders.models.StockListData
import com.nat.greco.screens.stocks.domain.repository.StocksRepository
import com.nat.greco.screens.stocks.models.SearchRequest
import com.nat.greco.screens.stocks.models.StockRequest
import com.nat.greco.screens.stocks.models.returnsModel.ReturnedListData
import com.nat.greco.screens.stocks.models.returnsModel.ReturnsStockResponse
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