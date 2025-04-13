package com.nat.couriersapp.screens.courierDetails.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.nat.couriersapp.base.BaseViewModel
import com.nat.couriersapp.base.domain.userManager.GetUserDataManager
import com.nat.couriersapp.screens.courierDetails.domain.models.CourierBody
import com.nat.couriersapp.screens.courierDetails.domain.models.DeliveredRequest
import com.nat.couriersapp.screens.courierDetails.domain.models.WaybillSerial
import com.nat.couriersapp.screens.courierDetails.domain.usecases.DeliveredCourierWithPODUseCase
import com.nat.couriersapp.screens.courierDetails.domain.usecases.GetRefusalReasonsUseCase
import com.nat.couriersapp.screens.courierDetails.domain.usecases.UpdateWaybillCourierStatusUseCase
import com.nat.couriersapp.screens.courierDetails.domain.usecases.StatusNotDeliveredUseCase
import com.nat.couriersapp.screens.courierDetails.domain.usecases.UpdatePickupCourierStatusUseCase
import com.nat.couriersapp.utils.getCurrentDate
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CourierDetailsViewModel(
    private val updateWaybillCourierStatusUseCase: UpdateWaybillCourierStatusUseCase,
    private val updatePickupCourierStatusUseCase: UpdatePickupCourierStatusUseCase,
    private val statusNotDeliveredUseCase: StatusNotDeliveredUseCase,
    private val getRefusalReasonsUseCase: GetRefusalReasonsUseCase,
    private val getUserDataManager: GetUserDataManager,
    private val deliveredCourierWithPODUseCase: DeliveredCourierWithPODUseCase
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
                        _state.update {
                            it.copy(
                                homeModel = event.model,
                                waybillSerial = event.model?.waybillSerial ?: 0
                            )
                        }

                    }

                    is CourierDetailsEvents.LocationChanged -> {
                        _state.update { it.copy(lat = event.lat, lng = event.lng) }
                    }

                    is CourierDetailsEvents.ClientSignatureChanged -> {
                        Log.d("BitmapTEST", event.image.toString())
                        _state.update { it.copy(image = event.image) }
                    }

                    is CourierDetailsEvents.NotDeliveredStatus -> {
                        callStatusNotDelivered()
                    }

                    is CourierDetailsEvents.BarCodeScannerValue -> {
                        _state.update { it.copy(waybillSerial = event.code) }
//                        Log.e("BarCodewaybillSerial", _state.value.waybillSerial.toString())
                        callUpdateWaybillStatusWithBarCode()
                    }

                    is CourierDetailsEvents.StatusChanged -> {
                        _state.update { it.copy(statusName = event.name, statusId = event.id) }
                        callRefusalReasonsApi()
                    }

                    is CourierDetailsEvents.RefusalWaybillReasonsChanged -> {
                        _state.update {
                            it.copy(
                                refusalName = event.name,
                                refusalId = event.id,
                                comments = event.comments,
                                lat = event.lat,
                                lng = event.lng
                            )
                        }
                        callWaybillNotDeliveredApi()
                    }

                    is CourierDetailsEvents.RefusalPickupReasonsChanged -> {
                        _state.update {
                            it.copy(
                                refusalName = event.name,
                                refusalId = event.id,
                                comments = event.comments,
                                lat = event.lat,
                                lng = event.lng
                            )
                        }
                        callPickupNotDeliveredApi()
                    }

                    is CourierDetailsEvents.TriggerWaybillDeliveredApi -> {
                        callWaybillDeliveredCourierApi()
                    }

                    is CourierDetailsEvents.TriggerPickupDeliveredApi -> {
                        callPickupDeliveredApi()
                    }

                    is CourierDetailsEvents.TriggerPickupNotDeliveredApi -> {
                        callPickupNotDeliveredApi()
                    }
                }
            }
        }
    }


    private fun callWaybillDeliveredCourierApi() {
        _state.update { it.copy(isLoading = true, errorMessage = "") }
        executeSuspend(
            block = {
                deliveredCourierWithPODUseCase(
                    deliveredRequest = DeliveredRequest(
                        comment = "",
                        fileBase64 = _state.value.image,
                        hubName = _state.value.homeModel?.hubName.orEmpty(),
                        incomingStatusId = null,
                        lastRefusalReasonId = null,
                        latitude = _state.value.lat,
                        longitude = _state.value.lng,
                        receiverName = _state.value.clientName,
                        receiverSSN = null,
                        roleId = "6",
                        userId = getUserDataManager.readUserId().first(),
                        userName = getUserDataManager.readName().first(),
                        waybillSerials = listOf(
                            WaybillSerial(
                                waybillId = _state.value.homeModel?.waybillId,
                                waybillSerial = _state.value.homeModel?.waybillSerial ?: 0

                            )
                        ),
                        zoneHubId = state.value.homeModel?.hubId ?: 0

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

    private fun callUpdateWaybillStatusWithBarCode() {
        _state.update { it.copy(isLoading = true, errorMessage = "") }
        executeSuspend(
            block = {
                updateWaybillCourierStatusUseCase(
                    LastStatusId = 83,
                    Comment = "",
                    LastRefusalReasonId = _state.value.homeModel?.lastStatusId ?: 0,
                    ActionDate = getCurrentDate(),
                    UserId = getUserDataManager.readUserId().first(),
                    UserName = getUserDataManager.readName().first(),
                    RoleId = getUserDataManager.readRoleId().first().toString(),
                    HubName = _state.value.homeModel?.hubName ?: "",
                    ZoneHubId = _state.value.homeModel?.hubId ?: 0,
                    Latitude = _state.value.lat,
                    Longitude = _state.value.lng,
                    courierBody = listOf(
                        CourierBody(
                            WaybillId = _state.value.homeModel?.waybillId ?: 0,
                            WaybillSerial = _state.value.waybillSerial
                        )
                    )
                )
            },
            onSuccess = { result ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = result?.message.orEmpty()
                    )
                }
            },
            onFailure = { error ->
                _state.update { it.copy(isLoading = false, errorMessage = error) }
            }
        )
    }

    private fun callWaybillNotDeliveredApi() {
        _state.update { it.copy(isLoading = true, errorMessage = "") }
        executeSuspend(
            block = {
                updateWaybillCourierStatusUseCase(
                    LastStatusId = _state.value.statusId,
                    Comment = _state.value.comments,
                    LastRefusalReasonId = _state.value.refusalId,
                    ActionDate = getCurrentDate(),
                    UserId = getUserDataManager.readUserId().first(),
                    UserName = getUserDataManager.readName().first(),
                    RoleId = "4",
                    HubName = _state.value.homeModel?.hubName.orEmpty(),
                    ZoneHubId = _state.value.homeModel?.hubId ?: 0,
                    Latitude = _state.value.lat,
                    Longitude = _state.value.lng,
                    courierBody = listOf(
                        CourierBody(
                            WaybillId = _state.value.homeModel?.waybillId ?: 0,
                            WaybillSerial = _state.value.homeModel?.waybillSerial ?: 0
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
            onFailure = {
                _state.update { it.copy(isLoading = false, errorMessage = it.errorMessage) }
            }
        )
    }

    private fun callPickupNotDeliveredApi() {
        _state.update { it.copy(isLoading = true, errorMessage = "") }
        executeSuspend(
            block = {
                updatePickupCourierStatusUseCase(
                    LastStatusId = _state.value.statusId,
                    Comment = _state.value.comments,
                    LastRefusalReasonId = _state.value.refusalId,
                    ActionDate = getCurrentDate(),
                    UserId = getUserDataManager.readUserId().first(),
                    UserName = getUserDataManager.readName().first(),
                    RoleId = "4",
                    HubName = _state.value.homeModel?.hubName.orEmpty(),
                    Latitude = _state.value.lat,
                    Longitude = _state.value.lng,
                    courierBody = listOf(
                        CourierBody(
                            WaybillId = _state.value.homeModel?.waybillId ?: 0,
                            WaybillSerial = _state.value.homeModel?.waybillSerial ?: 0
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
            onFailure = {
                _state.update { it.copy(isLoading = false, errorMessage = it.errorMessage) }
            }
        )
    }

    private fun callPickupDeliveredApi() {
        _state.update { it.copy(isLoading = true, errorMessage = "") }
        executeSuspend(
            block = {
                updatePickupCourierStatusUseCase(
                    LastStatusId = 79,
                    Comment = _state.value.comments,
                    LastRefusalReasonId = 142,
                    ActionDate = getCurrentDate(),
                    UserId = getUserDataManager.readUserId().first(),
                    UserName = getUserDataManager.readName().first(),
                    RoleId = "4",
                    HubName = _state.value.homeModel?.hubName.orEmpty(),
                    Latitude = _state.value.lat,
                    Longitude = _state.value.lng,
                    courierBody = listOf(
                        CourierBody(
                            WaybillId = _state.value.homeModel?.waybillId ?: 0,
                            WaybillSerial = _state.value.homeModel?.waybillSerial ?: 0
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
        }, onFailure = { error, _ ->
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
        }, onFailure = { error, _ ->
            _state.update { it.copy(errorMessage = error) }
        }

        )
    }

}