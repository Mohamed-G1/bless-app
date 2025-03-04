package com.nat.couriersapp.screens.login.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nat.couriersapp.R
import com.nat.couriersapp.base.BaseViewModel
import com.nat.couriersapp.base.network.Resource
import com.nat.couriersapp.base.network.executeSuspend
import com.nat.couriersapp.screens.home.presentation.HomeEvents
import com.nat.couriersapp.screens.login.domain.models.LoginRequest
import com.nat.couriersapp.screens.login.domain.usecases.LoginUseCase
import com.nat.couriersapp.screens.login.domain.usecases.SaveUserUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val useCase: LoginUseCase,
    private val saveUserUseCase: SaveUserUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(LoginState())
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
                    is LoginEvents.UserNameChanged -> {
                        _state.update { it.copy(userName = event.userName) }
                    }

                    is LoginEvents.PasswordChanged -> {
                        _state.update { it.copy(password = event.password) }
                    }

                    is LoginEvents.SubmitUser -> {
                        if (isValidData(
                                userName = state.value.userName.orEmpty(),
                                password = state.value.password.orEmpty()
                            )
                        ) {
                            _state.update { it.copy(errorMessage = "", isLoading = true) }
                            executeSuspend(
                                block = {
                                    useCase(
                                        request = LoginRequest(
                                            UserName = state.value.userName.orEmpty(),
                                            Password = state.value.password.orEmpty()
                                        )
                                    )
                                },
                                onSuccess = { data ->
                                    // this line because the api is returned success in the user is exist or not exist
                                    val errorMessage =
                                        if (data?.message.equals("Login Successful")
                                                .not()
                                        ) data?.message else null
                                    _state.update {
                                        it.copy(
                                            isLoading = false,
                                            errorMessage = errorMessage,
                                            navigateToHome = data?.status ?: false

                                        )
                                    }
                                    if (data?.obj != null)
                                        viewModelScope.launch {
                                            saveUserUseCase(response = data)
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


    private fun isValidData(userName: String, password: String): Boolean {
        return when {
            userName.isEmpty() -> {
                _state.update {
                    it.copy(
                        isValidUserName = false,
                        userNameValidationMessage = R.string.please_fill_username
                    )
                }
                false
            }

            password.isEmpty() -> {
                _state.update {
                    it.copy(
                        isValidUserName = true,
                        isValidPassword = false,
                        passwordValidationMessage = R.string.please_fill_password
                    )
                }
                false
            }

            else -> {
                _state.update {
                    it.copy(isValidUserName = true, isValidPassword = true)
                }
                true
            }
        }
    }

}