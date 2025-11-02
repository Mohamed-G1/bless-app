package com.nat.greco.screens.priceList.domain.data

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.priceList.domain.models.PriceListRequest
import com.nat.greco.screens.priceList.domain.models.PriceListResponse
import kotlinx.coroutines.flow.Flow

interface PriceListRepository {
    suspend fun getPriceList(request: BaseRequest<PriceListRequest>): Flow<Resource<PriceListResponse>>
}