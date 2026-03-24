package com.nat.bless.screens.salespersonBonusScreen

import androidx.lifecycle.viewModelScope
import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseViewModel
import com.nat.bless.base.domain.userManager.GetUserDataManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SalespersonBonusViewModel(
    val getUserDataManager: GetUserDataManager,
    val repository: SalespersonBonusRepository
) : BaseViewModel() {
    private val _state = MutableStateFlow(SalespersonBonusState())
    val state = _state.asStateFlow()

    private val _intentChannel = Channel<SalespersonBonusEvents>(Channel.UNLIMITED)
    fun sendEvent(intent: SalespersonBonusEvents) {
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
                    is SalespersonBonusEvents.OnMonthSelected -> {
                        _state.update { it.copy(month = event.month) }
                        getBonusDetails(event.month)
                    }
                }
            }
        }
    }

    private fun getBonusDetails(month: String) {
        executeFlow(
            block = {
                repository.getBonusDetails(
                    request = BaseRequest(
                        params = BonusDetailsRequest(
                            token = getUserDataManager.readToken().first(),
                            month = month
                        )
                    )
                )
            },
            onLoading = { isLoading ->
                _state.value = _state.value.copy(isLoading = isLoading)
            },
            onSuccess = { response ->
                response?.let {
                    _state.value = _state.value.copy(bonusDetails = response.result.data)
                }
            },
            onFailure = { message, code ->
                _state.value = _state.value.copy(error = message)
            }
        )
    }
}