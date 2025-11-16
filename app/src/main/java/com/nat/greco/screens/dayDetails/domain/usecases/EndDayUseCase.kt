package com.nat.greco.screens.dayDetails.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.dayDetails.domain.data.DayDetailsRepository
import com.nat.greco.screens.dayDetails.domain.models.DayDetailsRequest

class EndDayUseCase(
    private val repository: DayDetailsRepository
) {
    suspend operator fun invoke(
        request: BaseRequest<DayDetailsRequest>
    ): Resource<BaseResponse<Any>> = repository.endDay(request)
}