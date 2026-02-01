package com.nat.bless.screens.home.domain.repository

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.home.domain.models.HomeResponse
import com.nat.bless.screens.home.domain.models.RouteRequest
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getRoutes(
        request: BaseRequest<RouteRequest>
    ): Flow<Resource<BaseResponse<HomeResponse>>>

    suspend fun sendLocation(
        userId: Int,
        latitude: String,
        longitude: String
    ): Resource<Any>
}