package com.nat.couriersapp.screens.courierDetails.domain.usecases

import com.nat.couriersapp.base.network.Resource
import com.nat.couriersapp.screens.courierDetails.domain.models.RefusalReasonsResponse
import com.nat.couriersapp.screens.courierDetails.domain.repository.CourierDetailsRepository
import kotlinx.coroutines.flow.Flow

class GetRefusalReasonsUseCase(
    private val repository: CourierDetailsRepository
) {

    suspend operator fun invoke(statusId: Int): Flow<Resource<RefusalReasonsResponse>> {
        return repository.getNotDeliveredRefusalReasons(statusId)
    }
}