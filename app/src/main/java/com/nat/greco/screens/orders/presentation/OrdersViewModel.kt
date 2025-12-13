package com.nat.greco.screens.orders.presentation

import androidx.lifecycle.viewModelScope
import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseViewModel
import com.nat.greco.base.domain.userManager.GetUserDataManager
import com.nat.greco.screens.orders.domain.models.OrdersRequest
import com.nat.greco.screens.orders.domain.usecases.GetOrdersListUseCase
import com.nat.greco.screens.orders.domain.usecases.GetReturnsListUseCase
import com.nat.greco.screens.receviceStock.presentation.ReceiveStockEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrdersViewModel(
    private val getUserDataManager: GetUserDataManager,
    private val getOrdersListUseCase: GetOrdersListUseCase,
    private val getReturnsListUseCase: GetReturnsListUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(OrdersState())
    val state = _state.asStateFlow()
    private val _intentChannel = Channel<OrdersEvents>(Channel.UNLIMITED)
    fun sendEvent(intent: OrdersEvents) {
        viewModelScope.launch {
            _intentChannel.send(intent)
        }
    }


    init {
        _state.update { it.copy(error = "") }
        callGetOrdersListApi()
        processEvents()
    }


    private fun processEvents() {
        viewModelScope.launch {
            _intentChannel.consumeAsFlow().collect { event ->
                when (event) {
                    is OrdersEvents.GetOrdersEvent -> {
                        callGetOrdersListApi()
                    }

                    is OrdersEvents.GetReturnsEvent -> {
                        callGetReturnsListApi()
                    }
                }
            }
        }
    }
    private fun callGetOrdersListApi() {
        _state.update { it.copy(error = "") }
        executeFlow(block = {
            getOrdersListUseCase.invoke(
                request = BaseRequest(
                    params = OrdersRequest(
                        token = getUserDataManager.readToken().first()
                    )
                )
            )
        }, onLoading = { value ->
            _state.update { it.copy(isLoading = value) }

        }, onSuccess = { result ->
            _state.update { it.copy(model = result?.result?.data ?: listOf()) }

        }, onFailure = { error, _ ->
            _state.update { it.copy(error = error) }
        }
        )
    }

    private fun callGetReturnsListApi() {
        _state.update { it.copy(error = "") }
        executeFlow(block = {
            getReturnsListUseCase.invoke(
                request = BaseRequest(
                    params = OrdersRequest(
                        token = getUserDataManager.readToken().first()
                    )
                )
            )
        }, onLoading = { value ->
            _state.update { it.copy(isLoading = value) }

        }, onSuccess = { result ->
            _state.update { it.copy(returnsModel = result?.result?.data ?: listOf()) }

        }, onFailure = { error, _ ->
            _state.update { it.copy(error = error) }
        }
        )
    }

}