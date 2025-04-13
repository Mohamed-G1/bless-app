package com.nat.couriersapp.screens.courierDetails.domain.usecases

import com.nat.couriersapp.base.network.Resource
import com.nat.couriersapp.screens.courierDetails.domain.models.DeliveredRequest
import com.nat.couriersapp.screens.courierDetails.domain.models.DeliveredResponse
import com.nat.couriersapp.screens.courierDetails.domain.repository.CourierDetailsRepository

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