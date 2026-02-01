package com.nat.bless.screens.priceList.presentation

import androidx.lifecycle.viewModelScope
import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseViewModel
import com.nat.bless.base.domain.userManager.GetUserDataManager
import com.nat.bless.screens.priceList.domain.models.PriceListRequest
import com.nat.bless.screens.priceList.domain.usecases.GetPriceListUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PriceListViewModel(
    private val getUserDataManager: GetUserDataManager,
    private val getPriceListUseCase: GetPriceListUseCase
) : BaseViewModel() {
    private val _state = MutableStateFlow(PriceListState())
    val state = _state.asStateFlow()


    private val _intentChannel = Channel<PriceListEvents>(Channel.UNLIMITED)
    fun sendEvent(intent: PriceListEvents) {
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
                    is PriceListEvents.GetCustomerId -> {
                        _state.update { it.copy(customerId = event.customerId) }
                        callPriceListApi()
                    }
                }
            }
        }
    }

    private fun callPriceListApi() {
        executeFlow(block = {
            getPriceListUseCase.invoke(
                request = BaseRequest(
                    params = PriceListRequest(
                        token = getUserDataManager.readToken().first(),
                        customer_id = state.value.customerId
                    )
                )
            )
        }, onLoading = { value ->
            _state.update { it.copy(isLoading = value) }

        }, onSuccess = { result ->
            _state.update { it.copy(priceListModel = result) }

        }, onFailure = { error, _ ->
            _state.update { it.copy(error = error) }
        }

        )
    }


}