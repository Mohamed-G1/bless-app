package com.nat.couriersapp.screens.courierDetails.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.nat.couriersapp.base.BaseViewModel
import com.nat.couriersapp.base.domain.userManager.GetUserDataManager
import com.nat.couriersapp.screens.courierDetails.domain.models.CourierBody
import com.nat.couriersapp.screens.courierDetails.domain.models.DeliveredRequest
import com.nat.couriersapp.screens.courierDetails.domain.models.WaybillSerial
import com.nat.couriersapp.screens.courierDetails.domain.usecases.DeliveredBarCodeCourierUseCase
import com.nat.couriersapp.screens.courierDetails.domain.usecases.DeliveredCourierUseCase
import com.nat.couriersapp.screens.courierDetails.domain.usecases.GetRefusalReasonsUseCase
import com.nat.couriersapp.screens.courierDetails.domain.usecases.NotDeliveredCourierUseCase
import com.nat.couriersapp.screens.courierDetails.domain.usecases.SendSignatureUseCase
import com.nat.couriersapp.screens.courierDetails.domain.usecases.StatusNotDeliveredUseCase
import com.nat.couriersapp.utils.createFilePart
import com.nat.couriersapp.utils.getCurrentDate
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class CourierDetailsViewModel(
    private val deliveredBarCodeCourierUseCase: DeliveredBarCodeCourierUseCase,
    private val notDeliveredCourierUseCase: NotDeliveredCourierUseCase,
    private val sendSignatureUseCase: SendSignatureUseCase,
    private val statusNotDeliveredUseCase: StatusNotDeliveredUseCase,
    private val getRefusalReasonsUseCase: GetRefusalReasonsUseCase,
    private val getUserDataManager: GetUserDataManager,
    private val deliveredCourierUseCase: DeliveredCourierUseCase
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
                        callDeliveredBarCodeCourierApi()
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

                    is CourierDetailsEvents.TriggerDeliveredApi -> {
                        callDeliveredCourierApi()
                    }
                }
            }
        }
    }


    private fun callDeliveredCourierApi() {
        _state.update { it.copy(isLoading = true, errorMessage = "") }
        executeSuspend(
            block = {
                deliveredCourierUseCase(
                   deliveredRequest = DeliveredRequest(
                       comment = "",
                       fileBase64 = _state.value.image,
                       hubName =  _state.value.homeModel?.hubName.orEmpty(),
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
                               waybillSerial =  _state.value.homeModel?.waybillSerial ?: 0

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
                        errorMessage = result?.message.orEmpty()
                    )
                }
            },
            onFailure = { error ->
                _state.update { it.copy(isLoading = false, errorMessage = error) }
            }
        )
    }

    private fun callDeliveredBarCodeCourierApi() {
        _state.update { it.copy(isLoading = true, errorMessage = "") }
        executeSuspend(
            block = {
                deliveredBarCodeCourierUseCase(
                    Comment = "",
                    LastRefusalReasonId = _state.value.homeModel?.lastStatusId ?: 0,
                    ActionDate = getCurrentDate(),
                    UserId = getUserDataManager.readUserId().first(),
                    UserName = getUserDataManager.readName().first(),
                    RoleId = "6",
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

    private fun callNotDeliveredApi() {
        _state.update { it.copy(isLoading = true, errorMessage = "") }
        executeSuspend(
            block = {
                notDeliveredCourierUseCase(
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
                        errorMessage = result?.message.orEmpty()
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