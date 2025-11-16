package com.nat.greco.screens.addNewClient.presentation

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseViewModel
import com.nat.greco.base.domain.userManager.GetUserDataManager
import com.nat.greco.screens.addNewClient.domain.models.AddCustomerRequest
import com.nat.greco.screens.addNewClient.domain.models.CustomerData
import com.nat.greco.screens.addNewClient.domain.usecases.AddCustomerUseCase
import com.nat.greco.screens.addNewClient.domain.usecases.GetCustomersUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddNewCustomerViewModel(
    private val getUserDataManager: GetUserDataManager,
    private val getCustomersUseCase: GetCustomersUseCase,
    private val addCustomerUseCase: AddCustomerUseCase
) : BaseViewModel() {
    private val _state = MutableStateFlow(AddNewCustomerState())
    val state = _state.asStateFlow()
    private val _intentChannel = Channel<AddNewCustomerEvents>(Channel.UNLIMITED)

    fun sendEvent(intent: AddNewCustomerEvents) {
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
                    is AddNewCustomerEvents.NameChanged -> {
                        _state.update { it.copy(name = event.name) }
                    }

                    is AddNewCustomerEvents.EmailChanged -> {
                        _state.update { it.copy(email = event.email) }
                    }

                    is AddNewCustomerEvents.PhoneChanged -> {
                        _state.update { it.copy(phone = event.phone) }

                    }

                    is AddNewCustomerEvents.AddressChanged -> {
                        _state.update { it.copy(address = event.address) }

                    }

                    is AddNewCustomerEvents.ContractChanged -> {
                        _state.update { it.copy(contract = event.contract) }

                    }

                    is AddNewCustomerEvents.Submit -> {
                        if (isValidData(
                                name = state.value.name,
                                email = state.value.email,
                                phone = state.value.phone,
                                address = state.value.address,
                                contract = state.value.contract
                            )
                        ) {

                            callAddCustomerApi()
                        }
                    }
                }
            }
        }
    }


    private fun callAddCustomerApi() {
        _state.update { it.copy(isLoading = true, error = "") }
        executeSuspend(
            block = {
                addCustomerUseCase.invoke(
                    request = BaseRequest(
                        params = AddCustomerRequest(
                            customer_data = CustomerData(
                                address = state.value.address,
                                contract = state.value.contract,
                                email = state.value.email,
                                mobile = state.value.phone,
                                name = state.value.name,
                                phone = state.value.phone
                            ),
                            token = getUserDataManager.readToken().first()
                        )
                    )
                )
            },
            onSuccess = { result ->
                _state.update {
                    it.copy(
                        error = result?.result?.message.orEmpty(),
                        isLoading = false,
                        navigateBack = true
                    )
                }

            },
            onFailure = { error ->
                _state.update { it.copy(error = error, isLoading = false) }

            }
        )
    }

    private fun isValidData(
        name: String,
        email: String,
        phone: String,
        address: String,
        contract: String
    ): Boolean {
        val egyptianPhonePattern = Regex("^01[0-2,5][0-9]{8}\$") // Only Egyptian numbers

        return when {
            name.isEmpty() -> {
                _state.update {
                    it.copy(
                        isValidName = false,
                        nameValidationMessage = "برجاء ادخال الاسم"
                    )
                }
                false
            }

            phone.isEmpty() -> {
                _state.update {
                    it.copy(
                        isValidName = true,
                        isValidPhone = false,
                        phoneValidationMessage = "برجاء ادخال رقم الموبايل"
                    )
                }
                false
            }

            !phone.matches(egyptianPhonePattern) -> {
                _state.update {
                    it.copy(
                        isValidName = true,
                        isValidPhone = false,
                        phoneValidationMessage = "برجاء ادخال رقم موبايل صحيح"
                    )
                }
                false
            }

            email.isEmpty() -> {
                _state.update {
                    it.copy(
                        isValidName = true,
                        isValidPhone = true,
                        isValidEmail = false,
                        emailValidationMessage = "برجاء ادخال بريد الكتروني"
                    )
                }
                false
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _state.update {
                    it.copy(
                        isValidName = true,
                        isValidPhone = true,
                        isValidEmail = false,
                        emailValidationMessage = "برجاء ادخال بريد الكتروني صحيح"
                    )
                }
                false
            }

            address.isEmpty() -> {
                _state.update {
                    it.copy(
                        isValidName = true,
                        isValidPhone = true,
                        isValidEmail = true,
                        isValidAddress = false,
                        addressValidationMessage = "برجاء ادخال عنوان"
                    )
                }
                false
            }

            contract.isEmpty() -> {
                _state.update {
                    it.copy(
                        isValidName = true,
                        isValidPhone = true,
                        isValidEmail = true,
                        isValidAddress = true,
                        isValidContract = false,
                        contractValidationMessage = "برجاء ادخال تفاصيل"
                    )
                }
                false
            }

            else -> {
                _state.update {
                    it.copy(
                        isValidName = true,
                        isValidPhone = true,
                        isValidEmail = true,
                        isValidAddress = true,
                        isValidContract = true,
                    )
                }
                true
            }
        }
    }

}