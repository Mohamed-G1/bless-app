package com.nat.bless.screens.salespersonBonusScreen

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.Resource
import kotlinx.coroutines.flow.Flow

interface SalespersonBonusRepository {

    suspend fun getBonusDetails(
        request: BaseRequest<BonusDetailsRequest>
    ): Flow<Resource<BaseResponse<BonusDetailsResponse>>>
}