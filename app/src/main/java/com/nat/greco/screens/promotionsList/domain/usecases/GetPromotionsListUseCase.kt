package com.nat.greco.screens.promotionsList.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.screens.promotionsList.domain.models.PromotionRequest
import com.nat.greco.screens.promotionsList.domain.repository.PromotionRepository

class GetPromotionsListUseCase(
    private val repository: PromotionRepository
) {
    suspend operator fun invoke(request: BaseRequest<PromotionRequest>) =
        repository.getPromotionList(request)
}