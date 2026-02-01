package com.nat.bless.screens.accounts.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.accounts.domain.data.AccountsRepository
import com.nat.bless.screens.accounts.models.AccountsRequest
import com.nat.bless.screens.accounts.models.AccountsResponse
import kotlinx.coroutines.flow.Flow

class GetInvociesUseCase(
    private val repository: AccountsRepository
) {

    suspend fun invoke(request: BaseRequest<AccountsRequest>): Flow<Resource<AccountsResponse>> {
        return repository.getInvoiceList(request)
    }
}