package com.nat.bless.screens.priceList.domain.data

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.priceList.domain.models.PriceListRequest
import com.nat.bless.screens.priceList.domain.models.PriceListResponse
import kotlinx.coroutines.flow.Flow

interface PriceListRepository {
    suspend fun getPriceList(request: BaseRequest<PriceListRequest>): Flow<Resource<PriceListResponse>>
}