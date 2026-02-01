package com.nat.bless.screens.home.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.home.domain.models.HomeResponse
import com.nat.bless.screens.home.domain.models.RouteRequest
import com.nat.bless.screens.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class GetRoutesUseCase(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(
        request: BaseRequest<RouteRequest>
    ): Flow<Resource<BaseResponse<HomeResponse>>> {
        return repository.getRoutes(
            request = request
        )
    }
}