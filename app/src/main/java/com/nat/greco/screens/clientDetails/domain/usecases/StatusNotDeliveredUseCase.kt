package com.nat.greco.screens.clientDetails.domain.usecases

import com.nat.greco.base.network.Resource
import com.nat.greco.screens.clientDetails.domain.models.StatusNotDeliveredResponse
import com.nat.greco.screens.clientDetails.domain.repository.CourierDetailsRepository
import kotlinx.coroutines.flow.Flow

class StatusNotDeliveredUseCase(
    private val repository: CourierDetailsRepository
) {

    suspend operator fun invoke(statusTypeId : Int): Flow<Resource<StatusNotDeliveredResponse>> {
        return repository.getNotDeliveredStatus(isActive = true, statusTypeId = statusTypeId)
    }
}