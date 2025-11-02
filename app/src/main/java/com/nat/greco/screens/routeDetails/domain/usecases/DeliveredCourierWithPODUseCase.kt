package com.nat.greco.screens.routeDetails.domain.usecases

import com.nat.greco.base.network.Resource
import com.nat.greco.screens.routeDetails.domain.models.DeliveredRequest
import com.nat.greco.screens.routeDetails.domain.models.DeliveredResponse
import com.nat.greco.screens.routeDetails.domain.repository.RouteDetailsRepository

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