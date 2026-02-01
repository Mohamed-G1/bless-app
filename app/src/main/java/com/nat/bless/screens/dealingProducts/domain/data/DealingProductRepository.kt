package com.nat.bless.screens.dealingProducts.domain.data

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.dealingProducts.models.DealingProductsRequest
import com.nat.bless.screens.dealingProducts.models.DealingProductsResponse
import kotlinx.coroutines.flow.Flow

interface DealingProductRepository {
    suspend fun getDealingProducts(request: BaseRequest<DealingProductsRequest>): Flow<Resource<DealingProductsResponse>>
}