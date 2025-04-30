package com.nat.greco.screens.home.domain.repository

import com.nat.greco.base.network.Resource
import com.nat.greco.screens.home.domain.models.HomeResponse
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getCouriers(
        userId: Int,
        date: String,
        type: String,
        clientId: String,
        keyword: String,
        filterQuery: String
    ): Flow<Resource<HomeResponse>>

    suspend fun sendLocation(
        userId: Int,
        latitude: String,
        longitude: String
    ): Resource<Any>
}