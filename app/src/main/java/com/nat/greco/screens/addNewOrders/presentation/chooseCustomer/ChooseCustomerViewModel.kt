package com.nat.greco.screens.addNewOrders.presentation.chooseCustomer

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseViewModel
import com.nat.greco.base.domain.userManager.GetUserDataManager
import com.nat.greco.screens.addNewClient.domain.models.CustomerRequest
import com.nat.greco.screens.addNewClient.domain.usecases.GetCustomersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

class ChooseCustomerViewModel(
    private val getUserDataManager: GetUserDataManager,
    private val getCustomersUseCase: GetCustomersUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(ChooseCustomerState())
    val state = _state.asStateFlow()


    init {
        callGetCustomersApi()
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