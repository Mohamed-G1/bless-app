package com.nat.greco.screens.dealingProducts.domain.data

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.dealingProducts.models.DealingProductsRequest
import com.nat.greco.screens.dealingProducts.models.DealingProductsResponse
import kotlinx.coroutines.flow.Flow

interface DealingProductRepository {
    suspend fun getDealingProducts(request: BaseRequest<DealingProductsRequest>): Flow<Resource<DealingProductsResponse>>
}