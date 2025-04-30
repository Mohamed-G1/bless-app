package com.nat.greco.screens.home.domain.usecases

import com.nat.greco.base.network.Resource
import com.nat.greco.screens.home.domain.models.HomeResponse
import com.nat.greco.screens.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class GetCouriersUseCase(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(
        userId: Int,
        date: String,
        type: String,
        clientId: String,
        keyword: String,
        filterQuery: String
    ): Flow<Resource<HomeResponse>> {
        return repository.getCouriers(
            userId = userId,
            date = date,
            type = type,
            clientId = clientId,
            keyword = keyword,
            filterQuery = filterQuery
        )
    }

}