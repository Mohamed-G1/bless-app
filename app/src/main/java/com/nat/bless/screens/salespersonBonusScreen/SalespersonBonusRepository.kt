package com.nat.bless.screens.salespersonBonusScreen

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.salespersonScreen.SalesPersonTargetResponse
import com.nat.bless.screens.salespersonScreen.SalespersonTargetRequest
import kotlinx.coroutines.flow.Flow

interface SalespersonBonusRepository {

    suspend fun getBonusDetails(
        request: BaseRequest<BonusDetailsRequest>
    ): Flow<Resource<BaseResponse<BonusDetailsResponse>>>


    suspend fun getSalespersonTargetDetails(
        request: BaseRequest<SalespersonTargetRequest>
    ): Flow<Resource<BaseResponse<SalesPersonTargetResponse>>>
}