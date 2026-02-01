package com.nat.bless.screens.routeDetails.domain.usecases

import com.nat.bless.base.network.Resource
import com.nat.bless.screens.routeDetails.domain.models.RefusalReasonsResponse
import com.nat.bless.screens.routeDetails.domain.repository.RouteDetailsRepository
import kotlinx.coroutines.flow.Flow

class GetRefusalReasonsUseCase(
    private val repository: RouteDetailsRepository
) {

    suspend operator fun invoke(statusId: Int): Flow<Resource<RefusalReasonsResponse>> {
        return repository.getNotDeliveredRefusalReasons(statusId)
    }
}