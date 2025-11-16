package com.nat.greco.screens.returnsScreen

import androidx.lifecycle.viewModelScope
import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseViewModel
import com.nat.greco.base.domain.userManager.GetUserDataManager
import com.nat.greco.screens.orderDetails.domain.usecases.GetOrderDetailsUseCase
import com.nat.greco.screens.orderDetails.presentation.OrderDetailsEvents
import com.nat.greco.screens.orders.domain.models.OrderDetailsRequest
import com.nat.greco.screens.routeDetails.presentation.RouteDetailsEvents
import com.nat.greco.screens.routeDetails.presentation.RouteDetailsState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReturnsViewModel(
    private val getUserDataManager: GetUserDataManager,
    private val getOrderDetailsUseCase: GetOrderDetailsUseCase,
    private val useCase: SendReturnsUseCase
) : BaseViewModel() {
    private val _state = MutableStateFlow(ReturnsState())
    val state = _state.asStateFlow()

    private val _intentChannel = Channel<ReturnsEvents>(Channel.UNLIMITED)

    fun sendEvent(intent: ReturnsEvents) {
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

                    is ReturnsEvents.OrderIdChanged -> {
                        _state.update { it.copy(orderId = event.id) }
                        callOrderDetailsApi(event.id)
                    }

                    is ReturnsEvents.CounterChanged -> {
                        _state.update { it.copy(lines = event.lines) }
                    }

                    is ReturnsEvents.ReturnProducts -> {
                        callReturnsApi()
                    }
                }
            }
        }
    }

    private fun callReturnsApi() {
        _state.update { it.copy(error = "", isLoading = true) }
        executeSuspend(
            block = {
                useCase.invoke(
                    request = BaseRequest(
                        params = ReturnsRequest(
                            token = getUserDataManager.readToken().first(),
                            order_id = state.value.orderId,
                            lines = state.value.lines
                        )
                    )
                )
            },
            onSuccess = { result ->
                if (result?.result?.message == "Products returned successfully.") {
                    // navigate back
                    _state.update { it.copy(navigateBack = true) }

                } else {
                    _state.update {
                        it.copy(
                            error = result?.result?.message.orEmpty(),
                            isLoading = false
                        )
                    }
                }

            },
            onFailure = { error ->
                _state.update { it.copy(error = error, isLoading = false) }

            }
        )
    }

    private fun callOrderDetailsApi(orderId: Int) {
        _state.update { it.copy(error = "") }
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