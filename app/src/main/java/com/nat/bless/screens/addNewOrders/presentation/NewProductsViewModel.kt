package com.nat.bless.screens.addNewOrders.presentation

import androidx.lifecycle.viewModelScope
import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseViewModel
import com.nat.bless.base.domain.userManager.GetUserDataManager
import com.nat.bless.screens.addNewOrders.domain.usecases.AddToCartUseCase
import com.nat.bless.screens.addNewOrders.domain.usecases.GetProductsListUseCase
import com.nat.bless.screens.addNewOrders.models.AddToCartRequest
import com.nat.bless.screens.addNewOrders.models.Line
import com.nat.bless.screens.addNewOrders.models.NewProductRequest
import com.nat.bless.screens.addNewOrders.models.SelectedUnit
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewProductsViewModel(
    private val getUserDataManager: GetUserDataManager,
    private val getProductsListUseCase: GetProductsListUseCase,
    private val addToCartUseCase: AddToCartUseCase,
) : BaseViewModel() {

    private val _state = MutableStateFlow(NewProductsState())
    val state = _state.asStateFlow()
    private val _intentChannel = Channel<NewProductsEvents>(Channel.UNLIMITED)

    fun sendEvent(intent: NewProductsEvents) {
        viewModelScope.launch {
            _intentChannel.send(intent)
        }
    }

    init {
        callGetProductsApi()
        processEvents()
    }

    private fun processEvents() {
        viewModelScope.launch {
            _intentChannel.consumeAsFlow().collect { event ->
                when (event) {
                    is NewProductsEvents.AddToCart -> {
                        callAddToCartApi(event.selectedUnits)
                    }

                    is NewProductsEvents.CustomerIdChanged -> {
                        _state.update { it.copy(customerId = event.customerId) }
                    }
                    is NewProductsEvents.NavigationCompleted -> {
                        _state.update { it.copy(navigateToConfirmOrder = false) }

                    }
                }
            }
        }
    }

    private fun callAddToCartApi(units: List<SelectedUnit>) {
        val lines = units.map { unit ->
            Line(
                price = unit.price,
                product_id = unit.productId,
                quantity = unit.quantity,
                uom_id = unit.uomId
            )
        }
        executeFlow(
            block = {
                addToCartUseCase.invoke(
                    request = BaseRequest(
                        params = AddToCartRequest(
                            token = getUserDataManager.readToken().first(),
                            customer_id = state.value.customerId ?: 275422,
                            lines = lines,
                            route_id = getUserDataManager.readRouteId().first()
                        )
                    )
                )
            },
            onLoading = { value ->
                _state.update { it.copy(isLoading = value) }
            },
            onSuccess = { result ->
                if (result?.result?.code == 200) {
                    _state.update { it.copy(addToCartModel = result ,navigateToConfirmOrder = true) }
                }else {
                    _state.update { it.copy(errorMessage = result?.result?.message.orEmpty()) }
                }
            },
            onFailure = { error, code ->
                _state.update { it.copy(errorMessage = error) }

            }
        )
    }

    private fun callGetProductsApi() {
        executeFlow(
            block = {
                getProductsListUseCase.invoke(
                    request = BaseRequest(
                        params = NewProductRequest(
                            token = getUserDataManager.readToken().first(),
                            customer_id = state.value.customerId
                        )
                    )
                )
            },
            onLoading = { value ->
                _state.update { it.copy(isLoading = value) }
            },
            onSuccess = { result ->
                if (result?.result?.code != 200) {
                    // error
                    _state.update { it.copy(errorMessage = result?.result?.message.orEmpty()) }
                } else {
                    _state.update {
                        it.copy(
                            model = result
                        )
                    }
                }
            },
            onFailure = { error, code ->
                _state.update { it.copy(errorMessage = error) }

            }
        )
    }
}