package com.nat.bless.screens.collect.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.screens.collect.domain.models.CollectRequest
import com.nat.bless.screens.collect.domain.repository.CollectRepository

class GetJournalsUseCase(
    val repository: CollectRepository
) {
    suspend operator fun invoke(request: BaseRequest<CollectRequest>) =
        repository.getJournals(request)
}
