package com.nat.greco.screens.receviceStock.domain.repository

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.receviceStock.domain.models.ConfirmReceiveStockRequest
import com.nat.greco.screens.receviceStock.domain.models.ReceiveStockRequest
import com.nat.greco.screens.receviceStock.domain.models.ReceiveStockResponse
import kotlinx.coroutines.flow.Flow

interface ReceiveStockRepository {

    suspend fun getReceiveStock(
        request: BaseRequest<ReceiveStockRequest>
    ): Flow<Resource<BaseResponse<List<ReceiveStockResponse>>>>


    suspend fun confirmReceiveStock(
        request: BaseRequest<ConfirmReceiveStockRequest>
    ): Resource<BaseResponse<ReceiveStockResponse>>
}