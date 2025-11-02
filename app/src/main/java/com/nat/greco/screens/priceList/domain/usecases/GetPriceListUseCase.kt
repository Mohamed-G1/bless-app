package com.nat.greco.screens.priceList.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.screens.priceList.domain.data.PriceListRepository
import com.nat.greco.screens.priceList.domain.models.PriceListRequest

class GetPriceListUseCase(
    private val repository: PriceListRepository
) {

    suspend fun invoke(request: BaseRequest<PriceListRequest>) =
        repository.getPriceList(request)
}