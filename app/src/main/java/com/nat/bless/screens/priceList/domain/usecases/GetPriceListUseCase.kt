package com.nat.bless.screens.priceList.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.screens.priceList.domain.data.PriceListRepository
import com.nat.bless.screens.priceList.domain.models.PriceListRequest

class GetPriceListUseCase(
    private val repository: PriceListRepository
) {

    suspend fun invoke(request: BaseRequest<PriceListRequest>) =
        repository.getPriceList(request)
}