package com.nat.bless.screens.home.data.repository

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.ApiServices
import com.nat.bless.base.network.Resource
import com.nat.bless.base.network.safeApiCall
import com.nat.bless.screens.home.domain.models.HomeResponse
import com.nat.bless.screens.home.domain.models.RouteRequest
import com.nat.bless.screens.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class HomeRepositoryImpl(
    private val apiServices: ApiServices
) : HomeRepository {


    override suspend fun getRoutes(
        request: BaseRequest<RouteRequest>
    ): Flow<Resource<BaseResponse<HomeResponse>>> = flow {
        emit(Resource.Loading)
        val result = safeApiCall {
            apiServices.getRoutes(request)
        }
        emit(result)
    }.catch { exception ->
        emit(Resource.Error(message = exception.message.orEmpty()))
    }

    override suspend fun sendLocation(
        userId: Int,
        latitude: String,
        longitude: String
    ): Resource<Any> {
        return safeApiCall {
            apiServices.sendLocation(userId, latitude, longitude)
        }
    }
}