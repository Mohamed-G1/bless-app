package com.nat.greco.screens.courierDetails.domain.usecases

import com.nat.greco.base.network.Resource
import com.nat.greco.screens.courierDetails.domain.models.DeliveredRequest
import com.nat.greco.screens.courierDetails.domain.models.DeliveredResponse
import com.nat.greco.screens.courierDetails.domain.repository.CourierDetailsRepository

class DeliveredCourierWithPODUseCase(
    private val repository: CourierDetailsRepository

) {
    suspend operator fun invoke(
        deliveredRequest : DeliveredRequest
    ): Resource<DeliveredResponse> {
        return repository.deliveredCourierWithPOD(
            deliveredRequest  = deliveredRequest
        )
    }
}