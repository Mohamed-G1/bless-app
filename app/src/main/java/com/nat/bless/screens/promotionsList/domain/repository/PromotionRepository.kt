package com.nat.bless.screens.promotionsList.domain.repository

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.promotionsList.domain.models.PromotionRequest
import com.nat.bless.screens.promotionsList.domain.models.PromotionResponse
import kotlinx.coroutines.flow.Flow

interface PromotionRepository {
    suspend fun getPromotionList(
        request: BaseRequest<PromotionRequest>
    ): Flow<Resource<BaseResponse<List<PromotionResponse>>>>
}