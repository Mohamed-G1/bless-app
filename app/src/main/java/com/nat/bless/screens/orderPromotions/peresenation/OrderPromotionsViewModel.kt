package com.nat.bless.screens.orderPromotions.peresenation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseViewModel
import com.nat.bless.base.domain.userManager.GetUserDataManager
import com.nat.bless.screens.home.domain.models.CustomerId
import com.nat.bless.screens.orderPromotions.domain.models.AllowedProduct
import com.nat.bless.screens.orderPromotions.domain.models.AppliedProducts
import com.nat.bless.screens.orderPromotions.domain.models.ApplyPromotionsRequest
import com.nat.bless.screens.orderPromotions.domain.models.PromotionsRequest
import com.nat.bless.screens.orderPromotions.domain.repository.OrderPromotionsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderPromotionsViewModel(
    private val repository: OrderPromotionsRepository,
    private val getUserDataManager: GetUserDataManager
) : BaseViewModel() {

    private val _state = MutableStateFlow(OrderPromotionsState())
    val state = _state.asStateFlow()

    private val _intentChannel = Channel<OrderPromotionsEvents>(Channel.UNLIMITED)
    fun sendEvent(intent: OrderPromotionsEvents) {
        viewModelScope.launch {
            _intentChannel.send(intent)
        }
    }


    init {
        _state.update { it.copy(errorMessage = "") }
        processEvents()
    }

    private fun processEvents() {
        viewModelScope.launch {
            _intentChannel.consumeAsFlow().collect { event ->
                when (event) {
                    is OrderPromotionsEvents.GetOrderPromotions -> {
                        _state.update { it.copy(customerId = event.customerId) }

                        checkInternetConnection(
                            reTriggerApi = {
                                callGetOrdersPromotionsListApi()
                            }
                        )


                    }

                    is OrderPromotionsEvents.ApplyPromotion -> {
                        _state.update { it.copy(errorMessage = "") }
                        callApplyOrdersPromotionsListApi(selectedItem = event.selectedItems)
                    }
                }
            }
        }
    }


    private fun callGetOrdersPromotionsListApi() {
        _state.update { it.copy(errorMessage = "") }
        executeFlow(block = {
            repository.getBonus(
                request = BaseRequest(
                    params = PromotionsRequest(
                        token = getUserDataManager.readToken().first(),
                        customer_id = 303
                    )
                )
            )
        }, onLoading = { value ->
            _state.update { it.copy(isLoading = value) }

        }, onSuccess = { result ->
            _state.update { it.copy(model = result?.result?.data) }

            Log.d("PROMOTION_RESPONSE", "callGetOrdersPromotionsListApi: ")

        }, onFailure = { error, _ ->
            _state.update { it.copy(errorMessage = error) }
        }
        )
    }


    private fun callApplyOrdersPromotionsListApi(selectedItem: List<AllowedProduct>) {

        val items: List<AppliedProducts> = selectedItem.map {
            AppliedProducts(
                product_id = it.id,
                qty = 1
            )
        }

        _state.update { it.copy(errorMessage = "") }
        executeFlow(block = {
            repository.applyBonus(
                request = BaseRequest(
                    ApplyPromotionsRequest(
                        token = getUserDataManager.readToken().first(),
                        customer_id = state.value.customerId,
                        products = items
                    )
                )
            )
        }, onLoading = { value ->
            _state.update { it.copy(isLoading = value) }

        }, onSuccess = { result ->
            if (result?.result?.code != 200) {
                _state.update { it.copy(errorMessage = result?.result?.message.orEmpty()) }
            } else {
                _state.update { it.copy(canNavigateBack = true) }
            }

        }, onFailure = { error, _ ->
            _state.update { it.copy(errorMessage = error) }
        }
        )
    }

}