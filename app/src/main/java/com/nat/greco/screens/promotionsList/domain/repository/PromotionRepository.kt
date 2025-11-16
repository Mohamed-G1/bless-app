package com.nat.greco.screens.promotionsList.domain.repository

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.promotionsList.domain.models.PromotionRequest
import com.nat.greco.screens.promotionsList.domain.models.PromotionResponse
import kotlinx.coroutines.flow.Flow

interface PromotionRepository {
    suspend fun getPromotionList(
        request: BaseRequest<PromotionRequest>
    ): Flow<Resource<BaseResponse<List<PromotionResponse>>>>
}