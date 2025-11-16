package com.nat.greco.screens.promotionsList.presentation

import androidx.lifecycle.viewModelScope
import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseViewModel
import com.nat.greco.base.domain.userManager.GetUserDataManager
import com.nat.greco.screens.promotionsList.domain.models.PromotionRequest
import com.nat.greco.screens.promotionsList.domain.usecases.GetPromotionsListUseCase
import com.nat.greco.screens.receviceStock.domain.models.ReceiveStockRequest
import com.nat.greco.screens.receviceStock.presentation.ReceiveStockEvents
import com.nat.greco.screens.receviceStock.presentation.ReceiveStockState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PromotionViewModel(
    private val getUserDataManager: GetUserDataManager,
    private val getPromotionsListUseCase: GetPromotionsListUseCase
) : BaseViewModel() {
    private val _state = MutableStateFlow(PromotionState())
    val state = _state.asStateFlow()

    private val _intentChannel = Channel<PromotionEvents>(Channel.UNLIMITED)
    fun sendEvent(intent: PromotionEvents) {
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
                    is PromotionEvents.CustomerIdChanged -> {
                        callPromotionApi(event.id)
                    }
                }
            }
        }
    }

    private fun callPromotionApi(customerId: Int) {
        _state.update { it.copy(error = "") }
        executeFlow(block = {
            getPromotionsListUseCase.invoke(
                request = BaseRequest(
                    params = PromotionRequest(
                        token = getUserDataManager.readToken().first(),
                        customer_id = customerId
                    )
                )
            )
        }, onLoading = { value ->
            _state.update { it.copy(isLoading = value) }

        }, onSuccess = { result ->
            if (result?.result?.data?.isEmpty() == true) {
                _state.update { it.copy(error = result.result.message) }

            } else {
                _state.update { it.copy(model = result?.result?.data ?: listOf()) }
            }

        }, onFailure = { error, _ ->
            _state.update { it.copy(error = error) }
        })
    }


}