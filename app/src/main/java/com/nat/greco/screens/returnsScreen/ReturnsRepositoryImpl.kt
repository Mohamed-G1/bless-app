package com.nat.greco.screens.returnsScreen

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.ApiServices
import com.nat.greco.base.network.Resource
import com.nat.greco.base.network.safeApiCall

class ReturnsRepositoryImpl(
    private val apiServices: ApiServices
) : ReturnsRepository {
    override suspend fun sendReturns(request: BaseRequest<ReturnsRequest>): Resource<BaseResponse<Any>> =
        safeApiCall {
            apiServices.sendReturns(request)
        }
}