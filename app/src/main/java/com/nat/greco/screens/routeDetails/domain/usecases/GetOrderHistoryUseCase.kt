package com.nat.greco.screens.routeDetails.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.routeDetails.domain.models.OrderHistoryRequest
import com.nat.greco.screens.routeDetails.domain.models.OrderHistoryResponse
import com.nat.greco.screens.routeDetails.domain.repository.RouteDetailsRepository
import kotlinx.coroutines.flow.Flow

class GetOrderHistoryUseCase(
    private val repository: RouteDetailsRepository

){
     suspend operator fun invoke(request : BaseRequest<OrderHistoryRequest>): Flow<Resource<BaseResponse<List<OrderHistoryResponse>>>> {
         return repository.getOrderHistory(request)
     }
}
