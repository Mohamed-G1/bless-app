package com.nat.bless.screens.routeDetails.presentation

import androidx.lifecycle.viewModelScope
import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseViewModel
import com.nat.bless.base.domain.userManager.GetUserDataManager
import com.nat.bless.screens.routeDetails.domain.models.ConfirmedAndCancelledRequest
import com.nat.bless.screens.routeDetails.domain.usecases.DeliveredCourierWithPODUseCase
import com.nat.bless.screens.routeDetails.domain.usecases.GetRefusalReasonsUseCase
import com.nat.bless.screens.routeDetails.domain.usecases.HandleConfirmAndCancelRoutesUseCase
import com.nat.bless.screens.routeDetails.domain.usecases.UpdateWaybillCourierStatusUseCase
import com.nat.bless.screens.routeDetails.domain.usecases.StatusNotDeliveredUseCase
import com.nat.bless.screens.routeDetails.domain.usecases.UpdatePickupCourierStatusUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RouteDetailsViewModel(
    private val updateWaybillCourierStatusUseCase: UpdateWaybillCourierStatusUseCase,
    private val updatePickupCourierStatusUseCase: UpdatePickupCourierStatusUseCase,
    private val statusNotDeliveredUseCase: StatusNotDeliveredUseCase,
    private val getRefusalReasonsUseCase: GetRefusalReasonsUseCase,
    private val getUserDataManager: GetUserDataManager,
    private val deliveredCourierWithPODUseCase: DeliveredCourierWithPODUseCase,
    private val handleConfirmAndCancelRoutesUseCase: HandleConfirmAndCancelRoutesUseCase
) : BaseViewModel() {
    private val _state = MutableStateFlow(RouteDetailsState())
    val state = _state.asStateFlow()

    private val _intentChannel = Channel<RouteDetailsEvents>(Channel.UNLIMITED)

    fun sendEvent(intent: RouteDetailsEvents) {
        viewModelScope.launch {
            _intentChannel.send(intent)
        }
    }

    init {
        processEvents()
        callCancelledReasonsApi()
//        callConfirmedReasonsApi()
    }

    private fun processEvents() {
        viewModelScope.launch {
            _intentChannel.consumeAsFlow().collect { event ->
                when (event) {
                    is RouteDetailsEvents.ClientNameChanged -> {
                        _state.update { it.copy(clientName = event.name) }
                    }

                    is RouteDetailsEvents.HomeModelChanged -> {
                        _state.update {
                            it.copy(
                                homeModel = event.model,
                            )
                        }

                    }

                    is RouteDetailsEvents.LocationChanged -> {
//                        _state.update { it.copy(lat = event.lat, lng = event.lng) }
                    }

                    is RouteDetailsEvents.ClientSignatureChanged -> {
//                        Log.d("BitmapTEST", event.image.toString())
//                        _state.update { it.copy(image = event.image) }
                    }

                    is RouteDetailsEvents.CancelRouteReasonsChanged -> {
                        _state.update { it.copy(refusalId = event.reasonId) }

                        callCancelRouteApi(event.routeId, event.reasonId)
                    }
                    is RouteDetailsEvents.ConfirmRouteReasonsChanged -> {
//                        _state.update { it.copy(refusalId = event.reasonId) }

                        callConfirmRouteApi(event.routeId, event.reasonId)
                    }
                    is RouteDetailsEvents.RefusalPickupReasonsChanged -> {}
                }
            }
        }
    }


    private fun callConfirmRouteApi(route_id: Int, not_visited_reason_id: Int) {
        _state.update { it.copy(isLoading = true, errorMessage = "") }
        executeSuspend(
            block = {
                handleConfirmAndCancelRoutesUseCase.confirmRouteUseCase(
                    requestBody = BaseRequest(
                        params = ConfirmedAndCancelledRequest(
                            token = getUserDataManager.readToken().first(),
                            route_id = route_id,
                            not_visited_reason_id = not_visited_reason_id
                        )
                    )
                )
            },
            onSuccess = { result ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        navigateBack = true
                    )
                }
            },
            onFailure = { error ->
                _state.update { it.copy(isLoading = false, errorMessage = error) }
            }
        )
    }
    private fun callCancelRouteApi(route_id: Int, not_visited_reason_id: Int) {
        _state.update { it.copy(isLoading = true, errorMessage = "") }
        executeSuspend(
            block = {
                handleConfirmAndCancelRoutesUseCase.cancelRouteUseCase(
                    requestBody = BaseRequest(
                        params = ConfirmedAndCancelledRequest(
                            token = getUserDataManager.readToken().first(),
                            route_id = route_id,
                            not_visited_reason_id = not_visited_reason_id
                        )
                    )
                )
            },
            onSuccess = { result ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        navigateBack = true
                    )
                }
            },
            onFailure = { error ->
                _state.update { it.copy(isLoading = false, errorMessage = error) }
            }
        )
    }



    private fun callConfirmedReasonsApi() {
        executeFlow(block = {
            handleConfirmAndCancelRoutesUseCase.getConfirmReasonsUseCase()
        }, onLoading = { value ->
            _state.update { it.copy(isLoading = value) }

        }, onSuccess = { result ->

            _state.update { it.copy(confirmedAndCancelledModel = result) }
        }, onFailure = { error, _ ->
            _state.update { it.copy(errorMessage = error) }
        }
        )
    }


    private fun callCancelledReasonsApi() {
        executeFlow(block = {
            handleConfirmAndCancelRoutesUseCase.getCancelReasonsUseCase()
        }, onLoading = { value ->
            _state.update { it.copy(isLoading = value) }

        }, onSuccess = { result ->

            _state.update { it.copy(confirmedAndCancelledModel = result) }
        }, onFailure = { error, _ ->
            _state.update { it.copy(errorMessage = error) }
        }
        )
    }

}