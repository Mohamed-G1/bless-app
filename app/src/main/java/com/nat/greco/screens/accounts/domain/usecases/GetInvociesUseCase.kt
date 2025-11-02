package com.nat.greco.screens.accounts.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.accounts.domain.data.AccountsRepository
import com.nat.greco.screens.accounts.models.AccountsRequest
import com.nat.greco.screens.accounts.models.AccountsResponse
import kotlinx.coroutines.flow.Flow

class GetInvociesUseCase(
    private val repository: AccountsRepository
) {

    suspend fun invoke(request: BaseRequest<AccountsRequest>): Flow<Resource<AccountsResponse>> {
        return repository.getInvoiceList(request)
    }
}