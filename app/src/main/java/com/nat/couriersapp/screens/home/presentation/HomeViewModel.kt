package com.nat.couriersapp.screens.home.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.nat.couriersapp.base.BaseViewModel
import com.nat.couriersapp.base.domain.userManager.GetUserDataManager
import com.nat.couriersapp.base.local.LocalDataSource
import com.nat.couriersapp.screens.home.domain.models.CourierSheetTypes
import com.nat.couriersapp.screens.home.domain.models.SortOptions
import com.nat.couriersapp.screens.home.domain.models.HomeModel
import com.nat.couriersapp.screens.home.domain.usecases.GetCouriersUseCase
import com.nat.couriersapp.screens.home.domain.usecases.SendLocationUseCase
import com.nat.couriersapp.utils.getCurrentDate
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel(
    private val useCase: GetCouriersUseCase,
    private val getUserDataManager: GetUserDataManager,
    private val localDataSource: LocalDataSource,
    private val sendLocationUseCase: SendLocationUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _intentChannel = Channel<HomeEvents>(Channel.UNLIMITED)

    fun sendEvent(intent: HomeEvents) {
        viewModelScope.launch {
            _intentChannel.send(intent)
        }
    }

    init {
        processEvents()
        viewModelScope.launch {
            _state.update {
                it.copy(
                    userName = getUserDataManager.readName().first(),
                    courierType = localDataSource.readSelectedCourierType().first(),
                    waybillSortType = localDataSource.readWaybillSortValue().first(),
                    waybillFilterType = localDataSource.readWaybillFilterValue().first(),
                    pickupFilterType = localDataSource.readPickupFilterValue().first(),
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun callGetWaybillCouriersApi() {
        executeFlow(
            block = {
                useCase(
                    userId = getUserDataManager.readUserId().first(),
                    date = getCurrentDate(),
                    type = _state.value.courierType.orEmpty(),
                    clientId = "undefiend",
                    keyword = "",
                    filterQuery = localDataSource.readWaybillFilterValue().first()
                )
            },
            onLoading = { value ->
                _state.update { it.copy(isLoading = value) }
            },
            onSuccess = { result ->
                // sort the lst automatically in case there is a filter value is stored
                viewModelScope.launch {
                    if (localDataSource.readWaybillSortValue().first().isNotEmpty()) {
                        _state.update { currentState ->
                            val filteredItems =
                                sortItems(
                                    result?.obj?.data.orEmpty(),
                                    localDataSource.readWaybillSortValue().first()
                                )
                            currentState.copy(
                                model = result,
                                homeList = filteredItems
                            )
                        }
                        // if no sort stored return the original list
                    } else {
                        _state.update {
                            it.copy(
                                model = result,
                                homeList = result?.obj?.data.orEmpty()
                            )
                        }
                    }
                }


            },
            onFailure = { error, code ->
                _state.update { it.copy(errorMessage = error, errorCode = code) }

            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun callGetPickupsCouriersApi() {
        executeFlow(
            block = {
                useCase(
                    filterQuery = localDataSource.readPickupFilterValue().first(),
                    userId = getUserDataManager.readUserId().first(),
                    date = getCurrentDate(),
                    type = _state.value.courierType.orEmpty(),
                    clientId = "undefiend",
                    keyword = ""

                )
            },
            onLoading = { value ->
                _state.update { it.copy(isLoading = value) }
            },
            onSuccess = { result ->
                // sort the lst automatically in case there is a filter value is stored
                viewModelScope.launch {
                    if (localDataSource.readWaybillSortValue().first().isNotEmpty()) {
                        _state.update { currentState ->
                            val filteredItems =
                                sortItems(
                                    result?.obj?.data.orEmpty(),
                                    localDataSource.readWaybillSortValue().first()
                                )
                            currentState.copy(
                                model = result,
                                homeList = filteredItems
                            )
                        }
                        // if no sort stored return the original list
                    } else {
                        _state.update {
                            it.copy(
                                model = result,
                                homeList = result?.obj?.data.orEmpty()
                            )
                        }
                    }
                }


            },
            onFailure = { error, code ->
                _state.update { it.copy(errorMessage = error, errorCode = code) }

            }
        )
    }

    private fun callSendLocationApi(
        lat: String,
        lng: String
    ) {
        _state.update { it.copy(errorMessage = "") }
        executeSuspend(
            block = {
                sendLocationUseCase(
                    userId = getUserDataManager.readUserId().first(),
                    latitude = lat,
                    longitude = lng
                )
            },
            onSuccess = {

            },
            onFailure = { error ->
                _state.update { it.copy(errorMessage = error) }
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun processEvents() {
        viewModelScope.launch {
            _intentChannel.consumeAsFlow().collect { event ->
                when (event) {
                    is HomeEvents.RefreshCouriers -> {
                        if (_state.value.courierType == CourierSheetTypes.waybill.name){
                            callGetWaybillCouriersApi()
                        } else {
                            callGetPickupsCouriersApi()
                        }
                    }

                    is HomeEvents.CallWaybillCouriers -> {
                        localDataSource.saveSelectedCourierType(event.query)
                        _state.update { it.copy(courierType = event.query, errorMessage = "") }
                        callGetWaybillCouriersApi()

                    }

                    is HomeEvents.CallPickupCouriers -> {
                        localDataSource.saveSelectedCourierType(event.query)
                        _state.update { it.copy(courierType = event.query, errorMessage = "") }
                        callGetPickupsCouriersApi()

                    }
                    is HomeEvents.CallAllCouriers -> {
                        localDataSource.saveSelectedCourierType(event.query)
                        _state.update { it.copy(courierType = "null", errorMessage = "") }
                        callGetWaybillCouriersApi()

                    }

                    is HomeEvents.WaybillSortTypeClicked -> {
                        Log.d("filterValue", event.sort)
                        _state.update { it.copy(waybillSortType = event.sort) }

                        val filteredItems = sortItems(_state.value.homeList, event.sort)
                        _state.update { currentState ->
                            currentState.copy(
                                waybillSortType = event.sort,
                                homeList = filteredItems
                            )
                        }
                        localDataSource.saveWaybillSortValue(event.sort)
                    }

                    is HomeEvents.PickupSortTypeClicked -> {
                        _state.update { it.copy(pickupSortType = event.sort) }
                        val filteredItems = sortItems(_state.value.homeList, event.sort)
                        _state.update { currentState ->
                            currentState.copy(
                                pickupSortType = event.sort,
                                homeList = filteredItems
                            )
                        }

                        localDataSource.savePickupSortValue(event.sort)


                    }

                    is HomeEvents.ResetWaybillSortClicked -> {
                        _state.update { it.copy(waybillSortType = "") }
                        localDataSource.clearWaybillSortValue()

                        callGetWaybillCouriersApi()
                    }

                    is HomeEvents.ResetPickupSortClicked -> {
                        _state.update { it.copy(pickupSortType = "") }
                        localDataSource.clearPickupSortValue()

                        callGetPickupsCouriersApi()
                    }

                    is HomeEvents.WaybillFilterTypeClicked -> {
                        _state.update { it.copy(waybillFilterType = event.filter) }
                        localDataSource.saveWaybillFilterValue(event.filter)

                        callGetWaybillCouriersApi()
                    }

                    is HomeEvents.SendFrequentlyLocation -> {
                        callSendLocationApi(
                            lat = event.lat.toString(),
                            lng = event.long.toString()
                        )
                    }

                    is HomeEvents.WaybillResetFilterClicked -> {
                        _state.update { it.copy(waybillFilterType = "") }
                        localDataSource.clearWaybillFilterValue()

                        callGetWaybillCouriersApi()
                    }

                    is HomeEvents.PickupFilterTypeClicked -> {
                        _state.update { it.copy(pickupFilterType = event.filter) }
                        localDataSource.savePickupFilterValue(event.filter)

                        callGetPickupsCouriersApi()
                    }

                    HomeEvents.PickupResetFilterClicked -> {
                        _state.update { it.copy(pickupFilterType = "") }
                        localDataSource.clearPickupFilterValue()

                        callGetPickupsCouriersApi()
                    }
                }
            }
        }
    }


    // sort logic
    private fun sortItems(items: List<HomeModel>?, filterType: String): List<HomeModel> {
        return when (filterType) {
            SortOptions.FromLatestToOldest.name -> {
                items?.sortedByDescending { it.waybillPickupDate } ?: emptyList()
            }

            SortOptions.FromOldestToLatest.name -> {
                items?.sortedBy { it.waybillPickupDate } ?: emptyList()
            }

            SortOptions.HighestAmount.name -> {
                items?.sortedByDescending { it.collectCharges } ?: emptyList()
            }

            SortOptions.LowestAmount.name -> {
                items?.sortedBy { it.collectCharges } ?: emptyList()
            }

            else -> {
                items ?: emptyList()
            }
        }
    }

}