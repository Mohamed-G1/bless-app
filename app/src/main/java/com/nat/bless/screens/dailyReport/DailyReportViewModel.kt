package com.nat.bless.screens.dailyReport

import androidx.lifecycle.viewModelScope
import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseViewModel
import com.nat.bless.base.domain.userManager.GetUserDataManager
import com.nat.bless.screens.dayDetails.domain.models.DayDetailsRequest
import com.nat.bless.screens.dayDetails.domain.usecases.GetDayDetailsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DailyReportViewModel(
    private val getDayDetailsUseCase: GetDayDetailsUseCase,
    private val getUserDataManager: GetUserDataManager
) : BaseViewModel() {

    private val _state = MutableStateFlow(DailyReportState())
    val state = _state.asStateFlow()


    private val _intentChannel = Channel<DailyReportEvents>(Channel.UNLIMITED)

    fun sendEvent(intent: DailyReportEvents) {
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
                    is DailyReportEvents.DataChanged -> {
                        _state.update { it.copy(date = event.date) }
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
                            date = state.value.date.orEmpty(),
                            route_id = getUserDataManager.readRouteId().first()

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

}