package com.nat.greco.screens.routeDetails.domain.usecases

import com.nat.greco.base.network.Resource
import com.nat.greco.screens.routeDetails.domain.models.RefusalReasonsResponse
import com.nat.greco.screens.routeDetails.domain.repository.RouteDetailsRepository
import kotlinx.coroutines.flow.Flow

class GetRefusalReasonsUseCase(
    private val repository: RouteDetailsRepository
) {

    suspend operator fun invoke(statusId: Int): Flow<Resource<RefusalReasonsResponse>> {
        return repository.getNotDeliveredRefusalReasons(statusId)
    }
}