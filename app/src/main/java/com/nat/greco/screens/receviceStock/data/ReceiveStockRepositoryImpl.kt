package com.nat.greco.screens.receviceStock.data

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.ApiServices
import com.nat.greco.base.network.Resource
import com.nat.greco.base.network.safeApiCall
import com.nat.greco.screens.receviceStock.domain.models.ConfirmReceiveStockRequest
import com.nat.greco.screens.receviceStock.domain.models.ReceiveStockRequest
import com.nat.greco.screens.receviceStock.domain.models.ReceiveStockResponse
import com.nat.greco.screens.receviceStock.domain.repository.ReceiveStockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ReceiveStockRepositoryImpl(
    private val apiServices: ApiServices
) : ReceiveStockRepository {
    override suspend fun getReceiveStock(request: BaseRequest<ReceiveStockRequest>): Flow<Resource<BaseResponse<List<ReceiveStockResponse>>>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.getReceiveStockList(request)
            }
            emit(result)
        }.catch { e ->
            emit(Resource.Error(e.message.toString()))
        }

    override suspend fun confirmReceiveStock(request: BaseRequest<ConfirmReceiveStockRequest>): Resource<BaseResponse<ReceiveStockResponse>> {
        return safeApiCall {
            apiServices.confirmReceiveStock(request)
        }
    }
}