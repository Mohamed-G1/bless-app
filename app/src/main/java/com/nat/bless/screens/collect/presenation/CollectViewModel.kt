package com.nat.bless.screens.collect.presenation

import androidx.lifecycle.viewModelScope
import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseViewModel
import com.nat.bless.base.domain.userManager.GetUserDataManager
import com.nat.bless.screens.collect.domain.models.CollectRequest
import com.nat.bless.screens.collect.domain.models.CreatePaymentRequest
import com.nat.bless.screens.collect.domain.models.CustomerSummeryRequest
import com.nat.bless.screens.collect.domain.usecases.CreatePaymentUseCase
import com.nat.bless.screens.collect.domain.usecases.GetCustomerSummeryUseCase
import com.nat.bless.screens.collect.domain.usecases.GetJournalsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CollectViewModel(
    val getUserDataManager: GetUserDataManager,
    val getJournalsUseCase: GetJournalsUseCase,
    val createPaymentUseCase: CreatePaymentUseCase,
    val getCustomerSummeryUseCase: GetCustomerSummeryUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(CollectState())
    val state = _state.asStateFlow()

    private val _intentChannel = Channel<CollectEvents>(Channel.UNLIMITED)
    fun sendEvent(intent: CollectEvents) {
        viewModelScope.launch {
            _intentChannel.send(intent)
        }
    }

    init {
        getJournals()
        getCustomerSummery()
        processEvents()
    }



    private fun processEvents() {
        viewModelScope.launch {
            _intentChannel.consumeAsFlow().collect { event ->
                when (event) {

                    is CollectEvents.CustomerIdChanged -> {
                        _state.update { it.copy(customerId = event.id) }
                    }

                    is CollectEvents.JournalIdChanged -> {
                        _state.update { it.copy(journalId = event.id) }
                    }

                    is CollectEvents.AmountChanged -> {
                        _state.update { it.copy(amount = event.amount) }
                    }

                    is CollectEvents.NoteChanged -> {
                        _state.update { it.copy(notes = event.note) }

                    }

                    CollectEvents.CreatePayment -> {
                        if (isValidData(
                                amount = state.value.amount ?: 0.0,
                                id = state.value.journalId
                            )
                        ){
                            callCreatePaymentApi()
                        }
                    }
                }
            }
        }
    }


    private fun callCreatePaymentApi() {
        _state.update { it.copy(isLoading = true, error = "") }
        executeSuspend(
            block = {
                createPaymentUseCase.invoke(
                    request = BaseRequest(
                        params = CreatePaymentRequest(
                            token = getUserDataManager.readToken().first(),
                            journal_id = state.value.journalId,
                            customer_id = state.value.customerId,
                            amount = state.value.amount ?: 0.0,
                            note = state.value.notes,
                            route_id = getUserDataManager.readRouteId().first()
                        )
                    )
                )
            },
            onSuccess = { value ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        navigateBack = true
                    )
                }
            },
            onFailure = { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = error
                    )
                }
            }
        )
    }
    private fun getCustomerSummery() {
        _state.update { it.copy(error = "") }
        executeFlow(
            block = {
                getCustomerSummeryUseCase.invoke(
                    request = BaseRequest(
                        params = CustomerSummeryRequest(
                            token = getUserDataManager.readToken().first(),
                            customer_id = state.value.customerId.toString()
                        )
                    )
                )
            },
            onLoading = { value ->
                _state.update { it.copy(isLoading = value) }

            },
            onSuccess = { result ->
                _state.update { it.copy(
                    totalDept = result?.result?.data?.total_debt,
                    lastTotalOrder = result?.result?.data?.last_total_order
                ) }
            },
            onFailure = { error, _ ->
                _state.update { it.copy(error = error) }
            }
        )
    }

    private fun getJournals() {
        _state.update { it.copy(error = "") }

        executeFlow(
            block = {
                getJournalsUseCase.invoke(
                    request = BaseRequest(
                        params = CollectRequest(
                            token = getUserDataManager.readToken().first()
                        )
                    )
                )
            },
            onLoading = { value ->
                _state.update { it.copy(isLoading = value) }

            },
            onSuccess = { result ->
                _state.update { it.copy(model = result?.result?.data ?: listOf()) }
            },
            onFailure = { error, _ ->
                _state.update { it.copy(error = error) }
            }
        )
    }


    private fun isValidData(amount: Double, id: Int): Boolean {

        return when {
            amount == 0.0 -> {
                _state.update {
                    it.copy(
                        isValidAmount = false
                    )
                }
                false
            }

            id == 0 -> {
                _state.update {
                    it.copy(
                        isValidAmount = true,
                        isValidJournalId = false,
                    )
                }
                false
            }

            else -> {
                _state.update {
                    it.copy(isValidAmount = true, isValidJournalId = true)
                }
                true
            }
        }
    }
}