package com.nat.greco.screens.orderDetails.presentation

import androidx.lifecycle.viewModelScope
import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseViewModel
import com.nat.greco.base.domain.userManager.GetUserDataManager
import com.nat.greco.screens.orderDetails.domain.usecases.GetOrderDetailsUseCase
import com.nat.greco.screens.orderHistory.OrderHistoryEvents
import com.nat.greco.screens.orderHistory.OrderHistoryState
import com.nat.greco.screens.orders.domain.models.OrderDetailsRequest
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderDetailsViewModel(
    private val getUserDataManager: GetUserDataManager,
    private val useCase: GetOrderDetailsUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(OrderDetailsState())
    val state = _state.asStateFlow()


    private val _intentChannel = Channel<OrderDetailsEvents>(Channel.UNLIMITED)
    fun sendEvent(intent: OrderDetailsEvents) {
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

                    is OrderDetailsEvents.OrderIdChanged -> {
                        callOrderDetailsApi(event.orderId)
                    }
                }
            }
        }
    }

    private fun callOrderDetailsApi(orderId: Int) {
        executeFlow(
            block = {
                useCase.invoke(
                    request = BaseRequest(
                        params = OrderDetailsRequest(
                            token = getUserDataManager.readToken().first(),
                            order_id = orderId.toString()
                        )
                    )
                )
            },
            onLoading = { value ->
                _state.update { it.copy(loading = value) }

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