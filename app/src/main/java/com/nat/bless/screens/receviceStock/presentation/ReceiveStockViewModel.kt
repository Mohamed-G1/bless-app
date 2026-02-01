package com.nat.bless.screens.receviceStock.presentation

import androidx.lifecycle.viewModelScope
import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseViewModel
import com.nat.bless.base.domain.userManager.GetUserDataManager
import com.nat.bless.screens.receviceStock.domain.models.ConfirmReceiveStockRequest
import com.nat.bless.screens.receviceStock.domain.models.ReceiveStockRequest
import com.nat.bless.screens.receviceStock.domain.usecases.ConfirmReceiveStockUseCase
import com.nat.bless.screens.receviceStock.domain.usecases.GetReceiveStockUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReceiveStockViewModel(
    private val getUserDataManager: GetUserDataManager,
    private val getReceiveStockUseCase: GetReceiveStockUseCase,
    private val confirmReceiveStockUseCase: ConfirmReceiveStockUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(ReceiveStockState())
    val state = _state.asStateFlow()

    private val _intentChannel = Channel<ReceiveStockEvents>(Channel.UNLIMITED)
    fun sendEvent(intent: ReceiveStockEvents) {
        viewModelScope.launch {
            _intentChannel.send(intent)
        }
    }


    init {
        _state.update { it.copy(error = "") }
        callReceiveStockApi()
        processEvents()
    }


    private fun processEvents() {
        viewModelScope.launch {
            _intentChannel.consumeAsFlow().collect { event ->
                when (event) {
                    is ReceiveStockEvents.ConfirmReceiveStock -> {
                        _state.update { it.copy(id = event.id) }
                        callConfirmReceiveStockApi()
                    }
                }
            }
        }
    }

    private fun callReceiveStockApi() {
        _state.update { it.copy(error = "") }
        executeFlow(block = {
            getReceiveStockUseCase.invoke(
                request = BaseRequest(
                    params = ReceiveStockRequest(
                        token = getUserDataManager.readToken().first()
                    )
                )
            )
        }, onLoading = { value ->
            _state.update { it.copy(isLoading = value) }

        }, onSuccess = { result ->
            if (result?.result?.data?.isEmpty() == true){
                _state.update { it.copy(error = result.result.message) }

            }else{
                _state.update { it.copy(model = result?.result?.data ?: listOf()) }
            }

        }, onFailure = { error, _ ->
            _state.update { it.copy(error = error) }
        })
    }


    private fun callConfirmReceiveStockApi() {
        _state.update { it.copy(error = "", isLoading = true) }
        executeSuspend(
            block = {
                confirmReceiveStockUseCase.invoke(
                    request = BaseRequest(
                        params = ConfirmReceiveStockRequest(
                            token = getUserDataManager.readToken().first(),
                            transfer_id = state.value.id
                        )
                    )
                )
            },
            onSuccess = { result ->
                if (result?.result?.data == null){
                    _state.update { it.copy(error = result?.result?.message.orEmpty()) }

                }else{
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.result.message,
                            navigateBack = true
                        )
                    }                }

            },
            onFailure = { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = error
                    )
                }
            }
        )
    }
}