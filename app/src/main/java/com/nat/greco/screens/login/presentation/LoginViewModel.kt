package com.nat.greco.screens.login.presentation

import androidx.lifecycle.viewModelScope
import com.nat.greco.R
import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseViewModel
import com.nat.greco.screens.login.domain.models.LoginRequest
import com.nat.greco.screens.login.domain.usecases.LoginUseCase
import com.nat.greco.screens.login.domain.usecases.SaveUserUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val useCase: LoginUseCase,
    private val saveUserUseCase: SaveUserUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(defaultLoginState())
    val state = _state.asStateFlow()

    private val _intentChannel = Channel<LoginEvents>(Channel.UNLIMITED)

    init {
        processEvents()
    }

    fun sendEvent(intent: LoginEvents) {
        viewModelScope.launch {
            _intentChannel.send(intent)
        }
    }

    private fun processEvents() {
        viewModelScope.launch {
            _intentChannel.consumeAsFlow().collect { event ->
                when (event) {
                    is LoginEvents.LocationFetched -> {
                        _state.update { it.copy(lat = event.lat, lang = event.long) }

                    }

                    is LoginEvents.PhoneNumberChanged -> {
                        _state.update { it.copy(mobile = event.phone) }
                    }

                    is LoginEvents.PasswordChanged -> {
                        _state.update { it.copy(password = event.password) }
                    }

                    is LoginEvents.SubmitUser -> {
                        if (isValidData(
                                mobile = state.value.mobile.orEmpty(),
                                password = state.value.password.orEmpty()
                            )
                        ) {
                            _state.update { it.copy(errorMessage = "", isLoading = true) }
                            executeSuspend(
                                block = {
                                    useCase.invoke(
                                        request = BaseRequest(
                                            params = LoginRequest(
                                                mobile = state.value.mobile.orEmpty(),
                                                password = state.value.password.orEmpty(),
                                                fcm_token = "123456",
                                                location_length = state.value.lat.orEmpty(),
                                                location_circles = state.value.lang.orEmpty()
                                            )
                                        )
                                    )
                                },
                                onSuccess = { data ->

                                    if (data?.result?.message == "User not exist") {
                                        _state.update {
                                            it.copy(
                                                isLoading = false,
                                                errorMessage = data?.result?.message,
                                            )
                                        }
                                    } else {
                                        viewModelScope.launch {
                                            if (data?.result?.data != null)
                                                saveUserUseCase(response = data.result.data)
                                        }

                                        _state.update {
                                            it.copy(
                                                isLoading = false,
                                                errorMessage = data?.result?.message,
                                                navigateToHome = true
                                            )
                                        }
                                    }
                                },
                                onFailure = { message ->
                                    _state.update {
                                        it.copy(
                                            isLoading = false,
                                            errorMessage = message
                                        )
                                    }
                                }
                            )


//                    viewModelScope.launch {
//                        useCase(
//                            request = LoginRequest(
//                                UserName = state.value.userName.orEmpty(),
//                                Password = state.value.password.orEmpty()
//                            )
//                        ).also { data ->
//                            when (data) {
//                                is Resource.Loading -> {
//                                    _state.update { it.copy(isLoading = data.status) }
//                                }
//
//                                is Resource.Success -> {
//                                    // this line because the api is returned success in the user is exist or not exist
//                                    val errorMessage =
//                                        if (data.data.message.equals("Login Successful")
//                                                .not()
//                                        ) data.data.message else null
//                                    _state.update {
//                                        it.copy(
//                                            isLoading = false,
//                                            errorMessage = errorMessage
//                                        )
//                                    }
//
//                                    if (data.data.obj != null)
//                                        saveUserUseCase(response = data.data)
//                                }
//
//                                is Resource.Error -> {
//                                    _state.update {
//                                        it.copy(
//                                            isLoading = false,
//                                            errorMessage = data.message
//                                        )
//                                    }
//                                }
//
//                                else -> {
//                                    _state.update { it.copy(isLoading = false) }
//                                }
//                            }
//                        }
//                    }
                        }
                    }

                    is LoginEvents.NavigationComplete -> {
                        _state.update { it.copy(navigateToHome = false) }
                    }
                }
            }
        }
    }


    private fun isValidData(mobile: String, password: String): Boolean {
        val egyptianPhonePattern = Regex("^01[0-2,5][0-9]{8}\$") // Only Egyptian numbers

        return when {
            mobile.isEmpty() -> {
                _state.update {
                    it.copy(
                        isValidMobile = false,
                        mobileValidationMessage = R.string.please_fill_mobile
                    )
                }
                false
            }

            !mobile.matches(egyptianPhonePattern) -> {
                _state.update {
                    it.copy(
                        isValidMobile = false,
                        mobileValidationMessage = R.string.please_fill_mobile
                    )
                }
                false
            }

            password.isEmpty() -> {
                _state.update {
                    it.copy(
                        isValidMobile = true,
                        isValidPassword = false,
                        passwordValidationMessage = R.string.please_fill_password
                    )
                }
                false
            }

            else -> {
                _state.update {
                    it.copy(isValidMobile = true, isValidPassword = true)
                }
                true
            }
        }
    }

}