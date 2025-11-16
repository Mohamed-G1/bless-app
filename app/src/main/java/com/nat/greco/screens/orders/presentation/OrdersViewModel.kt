package com.nat.greco.screens.orders.presentation

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseViewModel
import com.nat.greco.base.domain.userManager.GetUserDataManager
import com.nat.greco.screens.orders.domain.models.OrdersRequest
import com.nat.greco.screens.orders.domain.usecases.GetOrdersListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

class OrdersViewModel(
    private val getUserDataManager: GetUserDataManager,
    private val getOrdersListUseCase: GetOrdersListUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(OrdersState())
    val state = _state.asStateFlow()


    init {
        _state.update { it.copy(error = "") }
        callGetOrdersListApi()
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

}