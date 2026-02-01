package com.nat.bless.screens.accounts.domain.data

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.accounts.models.AccountsRequest
import com.nat.bless.screens.accounts.models.AccountsResponse
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {
    suspend fun getInvoiceList(
        request: BaseRequest<AccountsRequest>
    ): Flow<Resource<AccountsResponse>>
}