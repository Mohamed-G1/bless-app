package com.nat.bless.screens.orderPromotions.domain.repository

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.orderPromotions.domain.models.ApplyPromotionsRequest
import com.nat.bless.screens.orderPromotions.domain.models.OderPromotionsResponse
import com.nat.bless.screens.orderPromotions.domain.models.PromotionsRequest
import kotlinx.coroutines.flow.Flow

interface OrderPromotionsRepository {

    suspend fun getBonus(request : BaseRequest<PromotionsRequest>) : Flow<Resource<BaseResponse<OderPromotionsResponse>>>
    suspend fun applyBonus(request : BaseRequest<ApplyPromotionsRequest>) : Flow<Resource<BaseResponse<Any>>>
}