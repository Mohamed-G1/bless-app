package com.nat.bless.screens.editableConfirmOrder

import androidx.lifecycle.viewModelScope
import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseViewModel
import com.nat.bless.base.domain.userManager.GetUserDataManager
import com.nat.bless.screens.editableConfirmOrder.domain.models.DeleteOrderRequest
import com.nat.bless.screens.editableConfirmOrder.domain.models.EditableConfirmOrderRequest
import com.nat.bless.screens.editableConfirmOrder.domain.models.UpdateOrderRequest
import com.nat.bless.screens.orderDetails.domain.usecases.DeleteOrderListUseCase
import com.nat.bless.screens.orderDetails.domain.usecases.GetEditableOrderListUseCase
import com.nat.bless.screens.orderDetails.domain.usecases.UpdateOrderListUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditableConfirmOrderViewModel(
    private val getUserDataManager: GetUserDataManager,
    private val getEditableOrderListUseCase: GetEditableOrderListUseCase,
    private val updateOrderUseCase: UpdateOrderListUseCase,
    private val deleteOrderListUseCase: DeleteOrderListUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(EditableConfirmOrderState())
    val state = _state.asStateFlow()


    private val _intentChannel = Channel<EditableConfirmOrderEvents>(Channel.UNLIMITED)
    fun sendEvent(intent: EditableConfirmOrderEvents) {
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

                    is EditableConfirmOrderEvents.CustomerIdChanged -> {
                        _state.update { it.copy(customerId = event.customerId) }
                        callOrderListApi()
                    }

                    is EditableConfirmOrderEvents.UpdateItem -> {
                        callUpdateOrderApi(
                            quantity = event.counter,
                            lineId = event.lineId,
                            price = event.price
                        )
                    }

                    is EditableConfirmOrderEvents.DeleteItem -> {
                        callDeleteOrderApi(event.lineId)
                    }
                }
            }
        }
    }


    private fun callOrderListApi() {
        executeFlow(
            block = {
                getEditableOrderListUseCase.invoke(
                    request = BaseRequest(
                        params = EditableConfirmOrderRequest(
                            token = getUserDataManager.readToken().first(),
                            customer_id = state.value.customerId.toString()
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


    private fun callUpdateOrderApi(
        quantity: Int,
        lineId: Int,
        price: Double,
    ) {
        executeFlow(
            block = {
                updateOrderUseCase.invoke(
                    request = BaseRequest(
                        params = UpdateOrderRequest(
                            token = getUserDataManager.readToken().first(),
                            line_id = lineId,
                            price = price,
                            quantity = quantity,
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


    private fun callDeleteOrderApi(
        lineId: Int,
    ) {
        executeFlow(
            block = {
                deleteOrderListUseCase.invoke(
                    request = BaseRequest(
                        params = DeleteOrderRequest(
                            token = getUserDataManager.readToken().first(),
                            line_id = lineId,
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