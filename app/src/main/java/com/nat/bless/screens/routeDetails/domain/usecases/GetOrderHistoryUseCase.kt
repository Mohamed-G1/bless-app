package com.nat.bless.screens.routeDetails.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.routeDetails.domain.models.OrderHistoryRequest
import com.nat.bless.screens.routeDetails.domain.models.OrderHistoryResponse
import com.nat.bless.screens.routeDetails.domain.repository.RouteDetailsRepository
import kotlinx.coroutines.flow.Flow

class GetOrderHistoryUseCase(
    private val repository: RouteDetailsRepository

){
     suspend operator fun invoke(request : BaseRequest<OrderHistoryRequest>): Flow<Resource<BaseResponse<List<OrderHistoryResponse>>>> {
         return repository.getOrderHistory(request)
     }
}
