package com.nat.bless.screens.routeDetails.domain.usecases

import com.nat.bless.base.network.Resource
import com.nat.bless.screens.routeDetails.domain.models.StatusNotDeliveredResponse
import com.nat.bless.screens.routeDetails.domain.repository.RouteDetailsRepository
import kotlinx.coroutines.flow.Flow

class StatusNotDeliveredUseCase(
    private val repository: RouteDetailsRepository
) {

    suspend operator fun invoke(statusTypeId : Int): Flow<Resource<StatusNotDeliveredResponse>> {
        return repository.getNotDeliveredStatus(isActive = true, statusTypeId = statusTypeId)
    }
}