package com.nat.greco.screens.home.domain.repository

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.home.domain.models.HomeResponse
import com.nat.greco.screens.home.domain.models.RouteRequest
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