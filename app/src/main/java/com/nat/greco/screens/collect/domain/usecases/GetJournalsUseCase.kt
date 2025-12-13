package com.nat.greco.screens.collect.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.screens.collect.domain.models.CollectRequest
import com.nat.greco.screens.collect.domain.repository.CollectRepository

class GetJournalsUseCase(
    val repository: CollectRepository
) {
    suspend operator fun invoke(request: BaseRequest<CollectRequest>) =
        repository.getJournals(request)
}
