package com.nat.bless.screens.routeDetails.domain.usecases

import com.nat.bless.base.network.Resource
import com.nat.bless.screens.routeDetails.domain.models.DeliveredRequest
import com.nat.bless.screens.routeDetails.domain.models.DeliveredResponse
import com.nat.bless.screens.routeDetails.domain.repository.RouteDetailsRepository

class DeliveredCourierWithPODUseCase(
    private val repository: RouteDetailsRepository

) {
    suspend operator fun invoke(
        deliveredRequest : DeliveredRequest
    ): Resource<DeliveredResponse> {
        return repository.deliveredCourierWithPOD(
            deliveredRequest  = deliveredRequest
        )
    }
}