package com.nat.couriersapp.screens.home.data.repository

import com.nat.couriersapp.base.network.ApiServices
import com.nat.couriersapp.base.network.Resource
import com.nat.couriersapp.base.network.safeApiCall
import com.nat.couriersapp.screens.home.domain.models.HomeResponse
import com.nat.couriersapp.screens.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class HomeRepositoryImpl(
    private val apiServices: ApiServices
) : HomeRepository {

    override suspend fun getCouriers(
        userId: Int,
        date: String,
        type: String,
        clientId: String,
        keyword: String,
        filterQuery: String
    ): Flow<Resource<HomeResponse>> = flow {
        emit(Resource.Loading)
        val result = safeApiCall {
            apiServices.getCouriers(
                userId = userId,
                date = date,
                type = type,
                clientId = clientId,
                keyword = keyword,
                filterQuery = filterQuery
            )
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