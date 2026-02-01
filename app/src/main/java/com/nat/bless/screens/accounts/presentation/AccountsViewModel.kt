package com.nat.bless.screens.accounts.presentation

import androidx.lifecycle.viewModelScope
import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseViewModel
import com.nat.bless.base.domain.userManager.GetUserDataManager
import com.nat.bless.screens.accounts.domain.usecases.GetInvociesUseCase
import com.nat.bless.screens.accounts.models.AccountsRequest
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountsViewModel(
    private val getUserDataManager: GetUserDataManager,
    private val getInvociesUseCase: GetInvociesUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(AccountsState())
    val state = _state.asStateFlow()
    private val _intentChannel = Channel<AccountsEvents>(Channel.UNLIMITED)

    fun sendEvent(intent: AccountsEvents) {
        viewModelScope.launch {
            _intentChannel.send(intent)
        }
    }

    init {
        processEvents()
    }

    private fun processEvents() {
        viewModelScope.launch {
            _intentChannel.consumeAsFlow().collect { event ->
                when (event) {
                    is AccountsEvents.CustomerIdChanged -> {
                        _state.update { it.copy(customer_id = event.customerId) }
                        callGetInvcoicesApi()
                    }
                }
            }
        }
    }

    private fun callGetInvcoicesApi() {
        executeFlow(
            block = {
                getInvociesUseCase.invoke(
                    request = BaseRequest(
                        params = AccountsRequest(
                            token = getUserDataManager.readToken().first(),
                            customer_id = state.value.customer_id
                        )
                    )
                )
            },
            onLoading = { value ->
                _state.update { it.copy(isLoading = value) }
            },
            onSuccess = { result ->
                if (result?.result?.code != 200) {
                    // error
                    _state.update { it.copy(errorMessage = result?.result?.message.orEmpty()) }
                } else {
                    _state.update {
                        it.copy(
                            model = result
                        )
                    }
                }
            },
            onFailure = { error, code ->
                _state.update { it.copy(errorMessage = error) }

            }
        )
    }
}