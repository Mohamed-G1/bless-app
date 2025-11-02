package com.nat.greco.screens.dayDetails.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.dayDetails.domain.data.DayDetailsRepository
import com.nat.greco.screens.dayDetails.domain.models.DayDetailsRequest
import com.nat.greco.screens.dayDetails.domain.models.DayDetailsResponse
import kotlinx.coroutines.flow.Flow

class GetDayDetailsUseCase(
    private val repository: DayDetailsRepository
) {

    suspend operator fun invoke(
        request: BaseRequest<DayDetailsRequest>
    ): Flow<Resource<DayDetailsResponse>> {
        return repository.getDayDetails(
            request = request
        )
    }
}