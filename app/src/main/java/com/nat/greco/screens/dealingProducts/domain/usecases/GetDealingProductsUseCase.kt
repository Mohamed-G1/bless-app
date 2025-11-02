package com.nat.greco.screens.dealingProducts.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.screens.dealingProducts.domain.data.DealingProductRepository
import com.nat.greco.screens.dealingProducts.models.DealingProductsRequest

class GetDealingProductsUseCase(
    private val repository: DealingProductRepository
) {

    suspend fun invoke(request: BaseRequest<DealingProductsRequest>) =
        repository.getDealingProducts(request)
}