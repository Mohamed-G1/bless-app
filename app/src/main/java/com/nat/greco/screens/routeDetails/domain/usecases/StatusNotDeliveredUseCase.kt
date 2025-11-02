package com.nat.greco.screens.routeDetails.domain.usecases

import com.nat.greco.base.network.Resource
import com.nat.greco.screens.routeDetails.domain.models.StatusNotDeliveredResponse
import com.nat.greco.screens.routeDetails.domain.repository.RouteDetailsRepository
import kotlinx.coroutines.flow.Flow

class StatusNotDeliveredUseCase(
    private val repository: RouteDetailsRepository
) {

    suspend operator fun invoke(statusTypeId : Int): Flow<Resource<StatusNotDeliveredResponse>> {
        return repository.getNotDeliveredStatus(isActive = true, statusTypeId = statusTypeId)
    }
}