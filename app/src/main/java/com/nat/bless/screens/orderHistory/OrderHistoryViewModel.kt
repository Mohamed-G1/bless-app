package com.nat.bless.screens.orderHistory

import androidx.lifecycle.viewModelScope
import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseViewModel
import com.nat.bless.base.domain.userManager.GetUserDataManager
import com.nat.bless.screens.routeDetails.domain.models.OrderHistoryRequest
import com.nat.bless.screens.routeDetails.domain.usecases.GetOrderHistoryUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderHistoryViewModel(
    val getUserDataManager: GetUserDataManager,
    val getOrderHistoryUseCase: GetOrderHistoryUseCase,
) : BaseViewModel() {


    private val _state = MutableStateFlow(OrderHistoryState())
    val state = _state.asStateFlow()


    private val _intentChannel = Channel<OrderHistoryEvents>(Channel.UNLIMITED)
    fun sendEvent(intent: OrderHistoryEvents) {
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

                    is OrderHistoryEvents.GetOrderHistory -> {
                        callOrderHistoryApi(customerId = event.id)
                    }
                }
            }
        }
    }

    private fun callOrderHistoryApi(customerId: Int) {
        executeFlow(block = {
            getOrderHistoryUseCase.invoke(
                request = BaseRequest(
                    params = OrderHistoryRequest(
                        token = getUserDataManager.readToken().first(),
                        customer_id = customerId
                    )
                )
            )
        }, onLoading = { value ->
            _state.update { it.copy(isLoading = value) }

        }, onSuccess = { result ->

            _state.update { it.copy(model = result?.result?.data ?: listOf()) }

//            result?.result?.data?.forEach {
//                _state.update {
//                    it.copy(
//                        amount_tax = it.amount_tax,
//                        amount_total = it.amount_total,
//                        amount_untaxed = it.amount_untaxed,
//                        date_order = it.date_order,
//                        order_id = it.order_id,
//                        name = it.name
//                    )
//                }
//            }
//
//            _state.update { it.copy(model = result?.result?.data ?: listOf()) }
//
//
//            val allLines: List<OrderHistoryLine> =
//                result?.result?.data?.flatMap { it.order_lines } ?: emptyList()
//
//
//            allLines.forEach { line ->
//                _state.update {
//                    it.copy(
//                        discount = line.discount,
//                        id = line.id,
//                        price_subtotal = line.price_subtotal,
//                        price_unit = line.price_unit,
////                        product_id = line.product_id,
//                        product_uom = line.product_uom,
//                        product_uom_qty = line.product_uom_qty,
//                        qty_delivered = line.qty_delivered,
////                        taxes = line.taxes
//                    )
//                }
////                println("Line Item: ${line.product_id.name}") // or line.product_name if you have it
//            }


        }, onFailure = { error, _ ->
            _state.update { it.copy(errorMessage = error) }
        }

        )
    }


}