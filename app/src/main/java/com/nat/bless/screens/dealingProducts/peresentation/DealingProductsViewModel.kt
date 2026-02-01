package com.nat.bless.screens.dealingProducts.peresentation

import androidx.lifecycle.viewModelScope
import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseViewModel
import com.nat.bless.base.domain.userManager.GetUserDataManager
import com.nat.bless.screens.dealingProducts.domain.usecases.GetDealingProductsUseCase
import com.nat.bless.screens.dealingProducts.models.DealingProductsRequest
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DealingProductsViewModel(
    private val getUserDataManager: GetUserDataManager,
    private val getDealingProductsUseCase: GetDealingProductsUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(DealingProductsState())
    val state = _state.asStateFlow()


    private val _intentChannel = Channel<DealingProductsEvents>(Channel.UNLIMITED)
    fun sendEvent(intent: DealingProductsEvents) {
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
                    is DealingProductsEvents.CustomerIDChanged -> {
                        _state.update { it.copy(customerID = event.customerId) }
                        callOrderHistoryApi()
                    }
                }
            }
        }
    }


    private fun callOrderHistoryApi() {
        executeFlow(block = {
            getDealingProductsUseCase.invoke(
                request = BaseRequest(
                    params = DealingProductsRequest(
                        token = getUserDataManager.readToken().first(),
                        customer_id = state.value.customerID ?: 0
                    )
                )
            )
        }, onLoading = { value ->
            _state.update { it.copy(isLoading = value) }

        }, onSuccess = { result ->
            _state.update { it.copy(data = result?.result?.data ?: listOf()) }

        }, onFailure = { error, _ ->
            _state.update { it.copy(error = error) }
        }

        )
    }

}