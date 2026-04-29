package com.nat.bless.screens.orderPromotions.data

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.ApiServices
import com.nat.bless.base.network.Resource
import com.nat.bless.base.network.safeApiCall
import com.nat.bless.screens.orderPromotions.domain.models.ApplyPromotionsRequest
import com.nat.bless.screens.orderPromotions.domain.models.OderPromotionsResponse
import com.nat.bless.screens.orderPromotions.domain.models.PromotionsRequest
import com.nat.bless.screens.orderPromotions.domain.repository.OrderPromotionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class OrderPromotionsRepositoryImpl(
    private val apiServices: ApiServices
) : OrderPromotionsRepository {
    override suspend fun getBonus(request: BaseRequest<PromotionsRequest>): Flow<Resource<BaseResponse<OderPromotionsResponse>>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.getBonus(request)
            }
            emit(result)
        }.catch { e ->
            emit(Resource.Error(e.message.toString()))
        }

    override suspend fun applyBonus(request: BaseRequest<ApplyPromotionsRequest>): Flow<Resource<BaseResponse<Any>>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.applyBonus(request)
            }
            emit(result)
        }.catch { e ->
            emit(Resource.Error(e.message.toString()))
        }
}