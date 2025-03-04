package com.nat.couriersapp.screens.courierDetails.domain.usecases

import com.nat.couriersapp.base.network.Resource
import com.nat.couriersapp.screens.courierDetails.domain.models.StatusNotDeliveredResponse
import com.nat.couriersapp.screens.courierDetails.domain.repository.CourierDetailsRepository
import kotlinx.coroutines.flow.Flow

class StatusNotDeliveredUseCase(
    private val repository: CourierDetailsRepository
) {

    suspend operator fun invoke(): Flow<Resource<StatusNotDeliveredResponse>> {
        return repository.getNotDeliveredStatus(isActive = true)
    }
}