package com.nat.bless.screens.returnsScreen

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.Resource

interface ReturnsRepository {
    suspend fun sendReturns(request: BaseRequest<ReturnsRequest>): Resource<BaseResponse<Any>>
}