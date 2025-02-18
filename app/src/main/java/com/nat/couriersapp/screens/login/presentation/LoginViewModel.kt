package com.nat.couriersapp.screens.login.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nat.couriersapp.R
import com.nat.couriersapp.base.network.Resource
import com.nat.couriersapp.screens.login.domain.models.LoginRequest
import com.nat.couriersapp.screens.login.domain.usecases.LoginUseCase
import com.nat.couriersapp.screens.login.domain.usecases.SaveUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val useCase: LoginUseCase,
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onEvent(events: LoginEvents) {
        when (events) {
            is LoginEvents.UserNameChanged -> {
                _state.update { it.copy(userName = events.userName) }
            }

            is LoginEvents.PasswordChanged -> {
                _state.update { it.copy(password = events.password) }
            }

            is LoginEvents.SubmitUser -> {
                if (isValidData(
                        userName = state.value.userName.orEmpty(),
                        password = state.value.password.orEmpty()
                    )
                ) {
                    _state.update { it.copy(errorMessage = "", isLoading = true) }
                    viewModelScope.launch {
                        useCase(
                            request = LoginRequest(
                                UserName = state.value.userName.orEmpty(),
                                Password = state.value.password.orEmpty()
                            )
                        ).also { data ->
                            when (data) {
                                is Resource.Loading -> {
                                    _state.update { it.copy(isLoading = data.status) }
                                }

                                is Resource.Success -> {
                                    // this line because the api is returned success in the user is exist or not exist
                                    val errorMessage =
                                        if (data.data.message.equals("Login Successful")
                                                .not()
                                        ) data.data.message else null
                                    _state.update {
                                        it.copy(
                                            isLoading = false,
                                            errorMessage = errorMessage
                                        )
                                    }

                                    if (data.data.obj != null)
                                        saveUserUseCase(response = data.data)
                                }

                                is Resource.Error -> {
                                    _state.update {
                                        it.copy(
                                            isLoading = false,
                                            errorMessage = data.message
                                        )
                                    }
                                }

                                else -> {
                                    _state.update { it.copy(isLoading = false) }
                                }
                            }
                        }
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