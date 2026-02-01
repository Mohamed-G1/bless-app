package com.nat.bless.screens.returnsScreen

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.ApiServices
import com.nat.bless.base.network.Resource
import com.nat.bless.base.network.safeApiCall

class ReturnsRepositoryImpl(
    private val apiServices: ApiServices
) : ReturnsRepository {
    override suspend fun sendReturns(request: BaseRequest<ReturnsRequest>): Resource<BaseResponse<Any>> =
        safeApiCall {
            apiServices.sendReturns(request)
        }
}