package com.nat.bless.screens.promotionsList.data

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.ApiServices
import com.nat.bless.base.network.Resource
import com.nat.bless.base.network.safeApiCall
import com.nat.bless.screens.promotionsList.domain.models.PromotionRequest
import com.nat.bless.screens.promotionsList.domain.models.PromotionResponse
import com.nat.bless.screens.promotionsList.domain.repository.PromotionRepository
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