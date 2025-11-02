package com.nat.greco.screens.home.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.home.domain.models.HomeResponse
import com.nat.greco.screens.home.domain.models.RouteRequest
import com.nat.greco.screens.home.domain.repository.HomeRepository
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