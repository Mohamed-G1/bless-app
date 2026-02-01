package com.nat.bless.screens.dayDetails.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.dayDetails.domain.data.DayDetailsRepository
import com.nat.bless.screens.dayDetails.domain.models.DayDetailsRequest

class EndDayUseCase(
    private val repository: DayDetailsRepository
) {
    suspend operator fun invoke(
        request: BaseRequest<DayDetailsRequest>
    ): Resource<BaseResponse<Any>> = repository.endDay(request)
}