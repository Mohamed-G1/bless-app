package com.nat.couriersapp.screens.courierDetails.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.nat.couriersapp.base.BaseViewModel
import com.nat.couriersapp.screens.courierDetails.domain.models.CourierBody
import com.nat.couriersapp.screens.courierDetails.domain.usecases.DeliveredCourierUseCase
import com.nat.couriersapp.screens.courierDetails.domain.usecases.GetRefusalReasonsUseCase
import com.nat.couriersapp.screens.courierDetails.domain.usecases.NotDeliveredCourierUseCase
import com.nat.couriersapp.screens.courierDetails.domain.usecases.SendSignatureUseCase
import com.nat.couriersapp.screens.courierDetails.domain.usecases.StatusNotDeliveredUseCase
import com.nat.couriersapp.utils.createDefaultBitmap
import com.nat.couriersapp.utils.getCurrentDate
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CourierDetailsViewModel(
    private val deliveredCourierUseCase: DeliveredCourierUseCase,
    private val notDeliveredCourierUseCase: NotDeliveredCourierUseCase,
    private val sendSignatureUseCase: SendSignatureUseCase,
    private val statusNotDeliveredUseCase: StatusNotDeliveredUseCase,
    private val getRefusalReasonsUseCase: GetRefusalReasonsUseCase
) : BaseViewModel() {
    private val _state = MutableStateFlow(CourierDetailsState())
    val state = _state.asStateFlow()

    private val _intentChannel = Channel<CourierDetailsEvents>(Channel.UNLIMITED)

    fun sendEvent(intent: CourierDetailsEvents) {
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
                    is CourierDetailsEvents.ClientNameChanged -> {
                        _state.update { it.copy(clientName = event.name) }
                    }

                    is CourierDetailsEvents.HomeModelChanged -> {
                        _state.update { it.copy(homeModel = event.model) }

                    }

                    is CourierDetailsEvents.LocationChanged -> {
                        _state.update { it.copy(lat = event.lat, lng = event.lng) }
                    }

                    is CourierDetailsEvents.ClientSignatureChanged -> {
                        Log.d("BitmapTEST", event.bimap.toString())
                        _state.update { it.copy(bitmap = event.bimap) }
                    }

                    is CourierDetailsEvents.NotDeliveredStatus -> {
                        callStatusNotDelivered()
                    }

                    is CourierDetailsEvents.StatusChanged -> {
                        _state.update { it.copy(statusName = event.name, statusId = event.id) }
                        callRefusalReasonsApi()
                    }

                    is CourierDetailsEvents.RefusalReasonsChanged -> {
                        _state.update {
                            it.copy(
                                refusalName = event.name,
                                refusalId = event.id,
                                comments = event.comments,
                                lat = event.lat,
                                lng = event.lng
                            )
                        }

                        callNotDeliveredApi()
                    }

                    is CourierDetailsEvents.TriggerUserSignatureApi -> {
                        executeSuspend(
                            block = {
                                sendSignatureUseCase(
                                    file = _state.value.bitmap ?: createDefaultBitmap(),
                                    waybill = _state.value.homeModel?.waybillId ?: 0,
                                    receiverName = _state.value.clientName
                                )
                            },
                            onSuccess = {
                                // if success call delivered api
                                callDeliveredApi()

                            },
                            onFailure = {
                                callDeliveredApi()
                            }
                        )
                    }
                }
            }
        }
    }


    private fun callDeliveredApi() {
        _state.update { it.copy(isLoading = true, errorMessage = "") }
        executeSuspend(
            block = {
                deliveredCourierUseCase(
                    Comment = "",
                    LastRefusalReasonId = _state.value.homeModel?.lastStatusId ?: 0,
                    ActionDate = getCurrentDate(),
                    UserId = _state.value.homeModel?.consigneeId ?: 0,
                    UserName = _state.value.homeModel?.shipperName.orEmpty(),
                    RoleId = "4",
                    HubName = _state.value.homeModel?.hubName.orEmpty(),
                    ZoneHubId = _state.value.homeModel?.hubId ?: 0,
                    Latitude = _state.value.lat,
                    Longitude = _state.value.lng,
                    courierBody = listOf(
                        CourierBody(
                            WaybillId = _state.value.homeModel?.waybillId ?: 0,
                            WaybillSerial = _state.value.homeModel?.waybillSerial?.toInt() ?: 0
                        )
                    )
                )
            },
            onSuccess = {
                _state.update { it.copy(isLoading = false, errorMessage = it.errorMessage) }
            },
            onFailure = {
                _state.update { it.copy(isLoading = false, errorMessage = it.errorMessage) }
            }
        )
    }

    private fun callNotDeliveredApi() {
        _state.update { it.copy(isLoading = true, errorMessage = "") }
        executeSuspend(
            block = {
                notDeliveredCourierUseCase(
                    LastStatusId = _state.value.statusId,
                    Comment = _state.value.comments,
                    LastRefusalReasonId =  _state.value.refusalId,
                    ActionDate = getCurrentDate(),
                    UserId = _state.value.homeModel?.consigneeId ?: 0,
                    UserName = "test",
                    RoleId = "4",
                    HubName = "test",
                    ZoneHubId = _state.value.homeModel?.hubId ?: 0,
                    Latitude = _state.value.lat,
                    Longitude = _state.value.lng,
                    courierBody = listOf(
                        CourierBody(
                            WaybillId = _state.value.homeModel?.waybillId ?: 0,
                            WaybillSerial = _state.value.homeModel?.waybillSerial?.toInt() ?: 0
                        )
                    )
                )
            },
            onSuccess = {
                _state.update { it.copy(isLoading = false, errorMessage = it.errorMessage) }
            },
            onFailure = {
                _state.update { it.copy(isLoading = false, errorMessage = it.errorMessage) }
            }
        )
    }

    private fun callStatusNotDelivered() {
        executeFlow(block = {
            statusNotDeliveredUseCase()
        }, onLoading = { value ->
            _state.update { it.copy(isLoading = value) }

        }, onSuccess = { result ->

            _state.update { it.copy(statusNotDelivered = result?.obj ?: listOf()) }
        }, onFailure = { error ->
            _state.update { it.copy(errorMessage = error) }
        }

        )
    }

    private fun callRefusalReasonsApi() {
        executeFlow(block = {
            getRefusalReasonsUseCase(statusId = _state.value.statusId)
        }, onLoading = { value ->
            _state.update { it.copy(isLoading = value) }

        }, onSuccess = { result ->

            _state.update { it.copy(refusalStatusNotDelivered = result?.obj ?: listOf()) }
        }, onFailure = { error ->
            _state.update { it.copy(errorMessage = error) }
        }

        )
    }


}