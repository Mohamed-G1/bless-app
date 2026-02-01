package com.nat.bless.screens.dealingProducts.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.screens.dealingProducts.domain.data.DealingProductRepository
import com.nat.bless.screens.dealingProducts.models.DealingProductsRequest

class GetDealingProductsUseCase(
    private val repository: DealingProductRepository
) {

    suspend fun invoke(request: BaseRequest<DealingProductsRequest>) =
        repository.getDealingProducts(request)
}