package com.nat.greco.screens.clients

import androidx.lifecycle.viewModelScope
import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseViewModel
import com.nat.greco.base.domain.userManager.GetUserDataManager
import com.nat.greco.screens.addNewClient.domain.models.CustomerRequest
import com.nat.greco.screens.addNewClient.domain.usecases.GetCustomersUseCase
import com.nat.greco.screens.dailyReport.DailyReportEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CustomersViewModel(
    private val getUserDataManager: GetUserDataManager,
    private val getCustomersUseCase: GetCustomersUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(CutomersState())
    val state = _state.asStateFlow()

    private val _intentChannel = Channel<CustomersEvents>(Channel.UNLIMITED)

    fun sendEvent(intent: CustomersEvents) {
        viewModelScope.launch {
            _intentChannel.send(intent)
        }
    }




    init {
        processEvents()
        callGetCustomersApi()
    }

    private fun processEvents() {
        viewModelScope.launch {
            _intentChannel.consumeAsFlow().collect { event ->
                when (event) {
                    is CustomersEvents.FetchCustomers -> {
                        callGetCustomersApi()
                    }
                }
            }
        }
    }

    private fun callGetCustomersApi(){
        _state.update { it.copy(error = "") }
        executeFlow(
            block = {
                getCustomersUseCase.invoke(
                    request = BaseRequest(
                        params = CustomerRequest(
                            token = getUserDataManager.readToken().first(),
                        )
                    )
                )
            },
            onLoading = { value ->
                _state.update { it.copy(isLoading = value) }

            },
            onSuccess = { result ->
                _state.update { it.copy(customers = result?.result?.data ?: listOf()) }

            },
            onFailure = { error, _ ->
                _state.update { it.copy(error = error) }
            }
        )
    }

}