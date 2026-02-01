package com.nat.bless.screens.accounts.data

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.network.ApiServices
import com.nat.bless.base.network.Resource
import com.nat.bless.base.network.safeApiCall
import com.nat.bless.screens.accounts.domain.data.AccountsRepository
import com.nat.bless.screens.accounts.models.AccountsRequest
import com.nat.bless.screens.accounts.models.AccountsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class AccountsRepositoryImpl(
    private val apiService: ApiServices
) : AccountsRepository {
    override suspend fun getInvoiceList(request: BaseRequest<AccountsRequest>): Flow<Resource<AccountsResponse>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiService.getInvoiceHistory(request)
            }
            emit(result)
        }.catch { exception ->
            emit(Resource.Error(message = exception.message.orEmpty()))
        }
}