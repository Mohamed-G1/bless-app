package com.nat.bless.screens.confirmOrder

import androidx.lifecycle.viewModelScope
import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseViewModel
import com.nat.bless.base.domain.userManager.GetUserDataManager
import com.nat.bless.screens.orderDetails.domain.usecases.ConfirmOrderUseCase
import com.nat.bless.screens.orderDetails.domain.usecases.GetOrderDetailsUseCase
import com.nat.bless.screens.orders.domain.models.OrderDetailsRequest
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConfirmOrderViewModel(
    private val getUserDataManager: GetUserDataManager,
    private val getOrderDetailsUseCase: GetOrderDetailsUseCase,
    private val confirmOrderUseCase: ConfirmOrderUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(ConfirmOrderState())
    val state = _state.asStateFlow()


    private val _intentChannel = Channel<ConfirmOrderEvents>(Channel.UNLIMITED)
    fun sendEvent(intent: ConfirmOrderEvents) {
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

                    is ConfirmOrderEvents.OrderAndCustomerIdsChanged -> {
                        _state.update { it.copy(orderId = event.id, customerid = event.customerid) }

                        callOrderDetailsApi(event.id)
                    }

                    is ConfirmOrderEvents.ConfirmOrder -> {
                        callConfirmorderApi()
                    }

                    is ConfirmOrderEvents.NavigationComplete -> {
                        _state.update { it.copy(navigateToCollectScreen = false) }
                    }
                }
            }
        }
    }


    private fun callConfirmorderApi() {
        _state.update { it.copy(error = "", isLoading = true) }

        executeSuspend(
            block = {
                confirmOrderUseCase.invoke(
                    request = BaseRequest(
                        params = OrderDetailsRequest(
                            token = getUserDataManager.readToken().first(),
                            order_id = state.value.orderId.toString()
                        )
                    )
                )
            },
            onSuccess = { result ->
                _state.update {
                    it.copy(
                        error = result?.result?.message.orEmpty(),
                        isLoading = false,
                        navigateToCollectScreen = true
                    )
                }

            },
            onFailure = { error ->
                _state.update { it.copy(error = error, isLoading = false) }
            }
        )
    }

    private fun callOrderDetailsApi(orderId: Int) {
        executeFlow(
            block = {
                getOrderDetailsUseCase.invoke(
                    request = BaseRequest(
                        params = OrderDetailsRequest(
                            token = getUserDataManager.readToken().first(),
                            order_id = orderId.toString()
                        )
                    )
                )
            },
            onLoading = { value ->
                _state.update { it.copy(isLoading = value) }

            },
            onSuccess = { result ->
                _state.update { it.copy(model = result?.result?.data) }

            },
            onFailure = { error, _ ->
                _state.update { it.copy(error = error) }

            }
        )
    }

}