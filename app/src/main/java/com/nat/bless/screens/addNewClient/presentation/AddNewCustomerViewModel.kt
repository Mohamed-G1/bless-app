package com.nat.bless.screens.addNewClient.presentation

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseViewModel
import com.nat.bless.base.domain.userManager.GetUserDataManager
import com.nat.bless.screens.addNewClient.domain.models.AddCustomerRequest
import com.nat.bless.screens.addNewClient.domain.models.CitiesRequest
import com.nat.bless.screens.addNewClient.domain.models.CustomerData
import com.nat.bless.screens.addNewClient.domain.models.CustomerRequest
import com.nat.bless.screens.addNewClient.domain.models.StatesRequest
import com.nat.bless.screens.addNewClient.domain.usecases.AddCustomerUseCase
import com.nat.bless.screens.addNewClient.domain.usecases.GetAreasUseCase
import com.nat.bless.screens.addNewClient.domain.usecases.GetCitiesUseCase
import com.nat.bless.screens.addNewClient.domain.usecases.GetCountryUseCase
import com.nat.bless.screens.addNewClient.domain.usecases.GetCustomersUseCase
import com.nat.bless.screens.addNewClient.domain.usecases.GetStatesUseCase
import com.nat.bless.screens.addNewClient.domain.usecases.GetTagsUseCase
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
    private val addCustomerUseCase: AddCustomerUseCase,
    private val getAreasUseCase: GetAreasUseCase,

    val getCountryUseCase: GetCountryUseCase,
    val getStatesUseCase: GetStatesUseCase,
    val getCitiesUseCase: GetCitiesUseCase,
    val getTagsUseCase: GetTagsUseCase

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
        callCountriesApi()
        callAreasApi()
        callTagsApi()
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

                    is AddNewCustomerEvents.DistinctiveChanged  -> {
                        _state.update { it.copy(distinctive = event.distinctive) }
                    }

                    is AddNewCustomerEvents.TagsChanged -> {
                        _state.update { it.copy(tagId = event.tag) }
                    }

                    is AddNewCustomerEvents.Submit -> {
                        if (isValidData(
                                name = state.value.name,
                                email = state.value.email,
                                phone = state.value.phone,
                                tagId = state.value.tagId,
                                address = state.value.address,
                                country = state.value.countryId,
                                state = state.value.statesId,
                                area = state.value.area,
                                city = state.value.cityId,
                                lat = state.value.lat,
                                long = state.value.long,
                                distinctive = state.value.distinctive
                            )
                        ) {

                            callAddCustomerApi()
                        }
                    }

                    is AddNewCustomerEvents.CityChanged -> {
                        _state.update { it.copy(cityId = event.city) }


                    }

                    is AddNewCustomerEvents.AreaChanged -> {
                        _state.update { it.copy(area = event.area) }


                    }

                    is AddNewCustomerEvents.CountryChanged -> {
                        _state.update { it.copy(countryId = event.country) }
                        callStatesApi(event.country)


                    }

                    is AddNewCustomerEvents.StateChanged -> {
                        _state.update { it.copy(statesId = event.state) }
                        callCitiesApi(event.state)
                    }

                    is AddNewCustomerEvents.LocationChanged -> {
                        _state.update {
                            it.copy(
                                lat = event.lat,
                                long = event.long,
                                location = event.name
                            )
                        }
                    }
                }
            }
        }
    }


    private fun callTagsApi() {
        executeFlow(
            block = {
                getTagsUseCase.invoke()

            },
            onLoading = { value ->
                _state.update { it.copy(isLoading = value) }
            },
            onSuccess = { result ->
                _state.update { it.copy(tags = result) }

            },
            onFailure = { error, code ->
                _state.update { it.copy(error = error) }

            }
        )

    }

    private fun callCitiesApi(stateId: Int) {
        executeFlow(
            block = {
                getCitiesUseCase.invoke(
                    request = BaseRequest(
                        params = CitiesRequest(
                            token = getUserDataManager.readToken().first(),
                            state_id = stateId
                        )
                    )
                )
            },
            onLoading = { value ->
                _state.update { it.copy(isLoading = value) }
            },
            onSuccess = { result ->
                _state.update { it.copy(cities = result?.result?.data ?: listOf()) }

            },
            onFailure = { error, code ->
                _state.update { it.copy(error = error) }

            }
        )

    }


    private fun callAreasApi() {
        executeFlow(
            block = {
                getAreasUseCase.invoke(
                    request = BaseRequest(
                        params = CustomerRequest(
                            token = getUserDataManager.readToken().first(),
                        )
                    )
                )
            },
            onLoading = { value ->
                _state.update { it.copy(isLoading = value) }
            },
            onSuccess = { result ->
                _state.update { it.copy(areas = result?.result?.data ?: listOf()) }

            },
            onFailure = { error, code ->
                _state.update { it.copy(error = error) }

            }
        )

    }

    private fun callStatesApi(countryId: Int) {
        executeFlow(
            block = {
                getStatesUseCase.invoke(
                    request = BaseRequest(
                        params = StatesRequest(
                            token = getUserDataManager.readToken().first(),
                            country_id = countryId
                        )
                    )
                )
            },
            onLoading = { value ->
                _state.update { it.copy(isLoading = value) }
            },
            onSuccess = { result ->
                _state.update { it.copy(states = result?.result?.data ?: listOf()) }

            },
            onFailure = { error, code ->
                _state.update { it.copy(error = error) }

            }
        )

    }

    private fun callCountriesApi() {
        executeFlow(
            block = {
                getCountryUseCase.invoke(
                    request = BaseRequest(
                        params = CustomerRequest(
                            token = getUserDataManager.readToken().first(),
                        )
                    )
                )
            },
            onLoading = { value ->
                _state.update { it.copy(isLoading = value) }
            },
            onSuccess = { result ->
                _state.update { it.copy(countries = result?.result?.data ?: listOf()) }

            },
            onFailure = { error, code ->
                _state.update { it.copy(error = error) }

            }
        )

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
                                phone = state.value.phone,
                                country_id = state.value.countryId,
                                state_id = state.value.statesId,
                                city_id = state.value.cityId,
                                area_id = state.value.area,
                                distinctive_mark = state.value.distinctive,
                                location_length = state.value.lat.toString(),
                                location_circles = state.value.long.toString(),
                                tag_id = state.value.tagId
                            ),
                            token = getUserDataManager.readToken().first()
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
            onFailure = { error ->
                _state.update { it.copy(error = error, isLoading = false) }

            }
        )
    }

    private fun isValidData(
        name: String,
        email: String,
        phone: String,
        tagId: Int,
        address: String,
        country: Int,
        state: Int,
        area: Int,
        city: Int,
        lat: String,
        long: String,
        distinctive : String
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

            tagId == 0 -> {
                _state.update {
                    it.copy(
                        isValidName = true,
                        isValidPhone = true,
                        isValidEmail = true,
                        isValidAddress = true,
                        isValidTag = false,
                        countryValidationMessage = "برجاء اختار البلد"
                    )
                }
                false
            }
            country == 0 -> {
                _state.update {
                    it.copy(
                        isValidName = true,
                        isValidPhone = true,
                        isValidEmail = true,
                        isValidAddress = true,
                        isValidTag = true,
                        isValidCountry = false,
                        countryValidationMessage = "برجاء اختار البلد"
                    )
                }
                false
            }

            state == 0 -> {
                _state.update {
                    it.copy(
                        isValidName = true,
                        isValidPhone = true,
                        isValidEmail = true,
                        isValidAddress = true,
                        isValidCountry = true,
                        isValidTag = true,

                        isValidState = false,
                        stateValidationMessage = "برجاء اختار محافظة"
                    )
                }
                false
            }

            city == 0 -> {
                _state.update {
                    it.copy(
                        isValidName = true,
                        isValidPhone = true,
                        isValidEmail = true,
                        isValidAddress = true,
                        isValidCountry = true,
                        isValidState = true,
                        isValidTag = true,

                        isValidCity = false,
                        cityValidationMessage = "برجاء اختار مدينة"
                    )
                }
                false
            }

            area == 0 -> {
                _state.update {
                    it.copy(
                        isValidName = true,
                        isValidPhone = true,
                        isValidEmail = true,
                        isValidAddress = true,
                        isValidCountry = true,
                        isValidState = true,
                        isValidCity = true,
                        isValidTag = true,

                        isValidArea = false,
                        cityValidationMessage = "برجاء اختار مدينة"
                    )
                }
                false
            }


            distinctive.isEmpty() -> {
                _state.update {
                    it.copy(
                        isValidName = true,
                        isValidPhone = true,
                        isValidEmail = true,
                        isDistinctiveValid = false,
                        addressValidationMessage =  "برجاء ادخال عنوان"
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
                        addressValidationMessage =  "برجاء ادخال علامة مميزة"
                    )
                }
                false
            }

            (lat.isEmpty()  && long.isEmpty()) -> {
                _state.update {
                    it.copy(
                        isValidName = true,
                        isValidPhone = true,
                        isValidEmail = true,
                        isValidAddress = true,
                        isValidCountry = true,
                        isValidState = true,
                        isValidCity = true,
                        isValidArea = true,
                        isValidTag = true,

                        isValidLocation = false,
                        locationValidationMessage = "برجاء اختار الموقع"
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
                        isValidCountry = true,
                        isValidState = true,
                        isValidCity = true,
                        isValidArea = true,
                        isValidLocation = true,
                        isDistinctiveValid = true,
                        isValidTag = true,


                        )
                }
                true
            }
        }
    }

}