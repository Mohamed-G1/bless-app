package com.nat.bless.screens.home.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseViewModel
import com.nat.bless.base.domain.userManager.GetUserDataManager
import com.nat.bless.base.local.LocalDataSource
import com.nat.bless.screens.home.domain.models.RouteRequest
import com.nat.bless.screens.home.domain.usecases.GetRoutesUseCase
import com.nat.bless.screens.home.domain.usecases.SaveRouteIdUseCase
import com.nat.bless.screens.home.domain.usecases.SendLocationUseCase
import com.nat.bless.screens.login.domain.usecases.ClearUserUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel(
    private val useCase: GetRoutesUseCase,
    private val getUserDataManager: GetUserDataManager,
    private val localDataSource: LocalDataSource,
    private val sendLocationUseCase: SendLocationUseCase,
    private val clearUserUseCase: ClearUserUseCase,
    private val saveRouteIdUseCase: SaveRouteIdUseCase
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
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun callGetRoutesApi() {
        executeFlow(
            block = {
                useCase.invoke(
                    request = BaseRequest(
                        params = RouteRequest(
                            token = getUserDataManager.readToken().first(),
                            date = state.value.date.orEmpty()
                        )
                    )
                )
            },
            onLoading = { value ->
                _state.update { it.copy(isLoading = value) }
            },
            onSuccess = { result ->
                if (result?.result?.code != 200 || result.result.message == "Token is not valid") {
                    // error
                    _state.update { it.copy(errorMessage = result?.result?.message) }

                } else {
                    _state.update {
                        it.copy(
                            model = result.result.data
                        )
                    }

                    viewModelScope.launch {
                        saveRouteIdUseCase(id = result.result.data.id)

                    }

                }

                // sort the lst automatically in case there is a filter value is stored
//                viewModelScope.launch {
//                    if (localDataSource.readWaybillSortValue().first().isNotEmpty()) {
//                        _state.update { currentState ->
//                            val filteredItems =
//                                sortItems(
//                                    result?.obj?.data.orEmpty(),
//                                    localDataSource.readWaybillSortValue().first()
//                                )
//                            currentState.copy(
//                                model = result,
//                                homeList = filteredItems
//                            )
//                        }
//                        // if no sort stored return the original list
//                    } else {
//                        _state.update {
//                            it.copy(
//                                model = result,
//                                homeList = result?.obj?.data.orEmpty()
//                            )
//                        }
//                    }
//                }


            },
            onFailure = { error, code ->
                _state.update { it.copy(errorMessage = error) }

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
                    is HomeEvents.clearUser -> {
                        viewModelScope.launch {
                            clearUserUseCase.invoke()
                        }
                    }

                    is HomeEvents.DataChanged -> {
                        _state.update { it.copy(date = event.date) }
                        callGetRoutesApi()
                    }

                    is HomeEvents.RefreshCouriers -> {

                    }

                    is HomeEvents.CallWaybillCouriers -> {

                    }

                    is HomeEvents.CallPickupCouriers -> {
                    }

                    is HomeEvents.CallAllCouriers -> {

                    }

                    is HomeEvents.WaybillSortTypeClicked -> {
                    }

                    is HomeEvents.PickupSortTypeClicked -> {
                    }

                    is HomeEvents.ResetWaybillSortClicked -> {
                    }

                    is HomeEvents.ResetPickupSortClicked -> {
                    }

                    is HomeEvents.WaybillFilterTypeClicked -> {
                    }

                    is HomeEvents.SendFrequentlyLocation -> {

                    }

                    is HomeEvents.WaybillResetFilterClicked -> {

                    }

                    is HomeEvents.PickupFilterTypeClicked -> {
                    }

                    is HomeEvents.PickupResetFilterClicked -> {
                    }

                    is HomeEvents.CallRoutes -> {
                        _state.update { it.copy(errorMessage = "") }
                        checkInternetConnection(
                            reTriggerApi = {
                                callGetRoutesApi()
                            }
                        )
                    }
                }
            }
        }
    }
}


