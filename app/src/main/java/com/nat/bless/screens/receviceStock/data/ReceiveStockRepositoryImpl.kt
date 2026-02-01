package com.nat.bless.screens.receviceStock.data

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.ApiServices
import com.nat.bless.base.network.Resource
import com.nat.bless.base.network.safeApiCall
import com.nat.bless.screens.receviceStock.domain.models.ConfirmReceiveStockRequest
import com.nat.bless.screens.receviceStock.domain.models.ReceiveStockRequest
import com.nat.bless.screens.receviceStock.domain.models.ReceiveStockResponse
import com.nat.bless.screens.receviceStock.domain.repository.ReceiveStockRepository
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