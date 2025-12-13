package com.nat.greco.screens.dayDetails.presentation

import androidx.lifecycle.viewModelScope
import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseViewModel
import com.nat.greco.base.domain.userManager.GetUserDataManager
import com.nat.greco.screens.dayDetails.domain.models.DayDetailsRequest
import com.nat.greco.screens.dayDetails.domain.usecases.EndDayUseCase
import com.nat.greco.screens.dayDetails.domain.usecases.GetDayDetailsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DayDetailsViewModel(
    private val getDayDetailsUseCase: GetDayDetailsUseCase,
    private val endDayUseCase: EndDayUseCase,
    private val getUserDataManager: GetUserDataManager
) : BaseViewModel() {

    private val _state = MutableStateFlow(DayDetailsState())
    val state = _state.asStateFlow()

    private val _intentChannel = Channel<DayDetailsEvents>(Channel.UNLIMITED)

    fun sendEvent(intent: DayDetailsEvents) {
        viewModelScope.launch {
            _intentChannel.send(intent)
        }
    }

    init {
        callGetDayDetailsApi()
        processEvents()
    }


    private fun processEvents() {
        viewModelScope.launch {
            _intentChannel.consumeAsFlow().collect { event ->
                when (event) {
                    is DayDetailsEvents.DataChanged -> {
                        _state.update { it.copy(date = event.date) }
                    }

                    is DayDetailsEvents.EndData -> {
                        endDate()
                    }
                }
            }
        }
    }

    private fun callGetDayDetailsApi() {
        _state.update { it.copy(isLoading = true, error = "") }
        executeFlow(
            block = {
                getDayDetailsUseCase.invoke(
                    request = BaseRequest(
                        params = DayDetailsRequest(
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
                _state.update {
                    it.copy(
                        model = result,
                        error = result?.result?.message.orEmpty()
                    )
                }
            },
            onFailure = { error, _ ->
                _state.update { it.copy(error = error) }

            }
        )
    }

    private fun endDate() {
        _state.update { it.copy(isLoading = true, error = "") }
        executeSuspend(
            block = {
                endDayUseCase.invoke(
                    request = BaseRequest(
                        params = DayDetailsRequest(
                            token = getUserDataManager.readToken().first(),
                            date = state.value.date.orEmpty()
                        )
                    )
                )
            },
            onSuccess = { result ->
                _state.update {
                    it.copy(
                        error = result?.result?.message.toString(), isLoading = false
                    )
                }


            },
            onFailure = { error ->
                _state.update {
                    it.copy(
                        error = error,
                        isLoading = false
                    )
                }

            }
        )
    }
}