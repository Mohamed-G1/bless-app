package com.nat.bless.screens.receviceStock.domain.repository

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.receviceStock.domain.models.ConfirmReceiveStockRequest
import com.nat.bless.screens.receviceStock.domain.models.ReceiveStockRequest
import com.nat.bless.screens.receviceStock.domain.models.ReceiveStockResponse
import kotlinx.coroutines.flow.Flow

interface ReceiveStockRepository {

    suspend fun getReceiveStock(
        request: BaseRequest<ReceiveStockRequest>
    ): Flow<Resource<BaseResponse<List<ReceiveStockResponse>>>>


    suspend fun confirmReceiveStock(
        request: BaseRequest<ConfirmReceiveStockRequest>
    ): Resource<BaseResponse<ReceiveStockResponse>>
}