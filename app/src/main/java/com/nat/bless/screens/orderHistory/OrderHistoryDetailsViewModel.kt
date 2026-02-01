package com.nat.bless.screens.orderHistory

import androidx.lifecycle.viewModelScope
import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseViewModel
import com.nat.bless.base.domain.userManager.GetUserDataManager
import com.nat.bless.screens.orderDetails.domain.usecases.GetOrderDetailsUseCase
import com.nat.bless.screens.orders.domain.models.OrderDetailsRequest
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderHistoryDetailsViewModel(
    private val getUserDataManager: GetUserDataManager,
    private val getOrderDetailsUseCase: GetOrderDetailsUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(OrderHistoryDetailsState())
    val state = _state.asStateFlow()


    private val _intentChannel = Channel<OrderHistoryDetailsEvents>(Channel.UNLIMITED)
    fun sendEvent(intent: OrderHistoryDetailsEvents) {
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

                    is OrderHistoryDetailsEvents.OrderIdChanged -> {
                        callOrderHistoryApi(orderId = event.id)
                    }
                }
            }
        }
    }

    private fun callOrderHistoryApi(orderId: Int) {
        executeFlow(block = {
            getOrderDetailsUseCase.invoke(
                request = BaseRequest(
                    params = OrderDetailsRequest(
                        token = getUserDataManager.readToken().first(),
                        order_id = orderId.toString()
                    )
                )
            )
        }, onLoading = { value ->
            _state.update { it.copy(isLoading = value) }

        }, onSuccess = { result ->
            _state.update { it.copy(model = result?.result?.data) }

        }, onFailure = { error, _ ->
            _state.update { it.copy(error = error) }
        }

        )
    }

}