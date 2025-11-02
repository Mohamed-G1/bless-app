package com.nat.greco.screens.accounts.domain.data

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.accounts.models.AccountsRequest
import com.nat.greco.screens.accounts.models.AccountsResponse
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {
    suspend fun getInvoiceList(
        request: BaseRequest<AccountsRequest>
    ): Flow<Resource<AccountsResponse>>
}