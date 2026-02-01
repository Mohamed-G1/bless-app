package com.nat.bless.screens.promotionsList.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.screens.promotionsList.domain.models.PromotionRequest
import com.nat.bless.screens.promotionsList.domain.repository.PromotionRepository

class GetPromotionsListUseCase(
    private val repository: PromotionRepository
) {
    suspend operator fun invoke(request: BaseRequest<PromotionRequest>) =
        repository.getPromotionList(request)
}