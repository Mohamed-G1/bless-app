package com.nat.couriersapp.screens.login.presentation

import androidx.annotation.StringRes
import com.nat.couriersapp.R

data class LoginState(
    val isLoading: Boolean = false,
    val navigateToHome: Boolean = false,
    val errorMessage: String ? = null,

    val userName : String? = null,
    val password: String? = null,

    val isValidUserName : Boolean = true,
    @StringRes val userNameValidationMessage : Int = R.string.please_fill_username,

    val isValidPassword : Boolean = true,
    @StringRes val passwordValidationMessage : Int = R.string.please_fill_password
)
