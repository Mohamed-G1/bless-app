package com.nat.greco.screens.returnsScreen

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.Resource

interface ReturnsRepository {
    suspend fun sendReturns(request: BaseRequest<ReturnsRequest>): Resource<BaseResponse<Any>>
}