package com.nat.greco.screens.promotionsList.data

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.ApiServices
import com.nat.greco.base.network.Resource
import com.nat.greco.base.network.safeApiCall
import com.nat.greco.screens.promotionsList.domain.models.PromotionRequest
import com.nat.greco.screens.promotionsList.domain.models.PromotionResponse
import com.nat.greco.screens.promotionsList.domain.repository.PromotionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PromotionRepositoryImpl(
    private val apiServices: ApiServices
) : PromotionRepository {
    override suspend fun getPromotionList(request: BaseRequest<PromotionRequest>): Flow<Resource<BaseResponse<List<PromotionResponse>>>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.getPromotionList(request)
            }
            emit(result)
        }.catch { e ->
            emit(Resource.Error(e.message.toString()))
        }
}