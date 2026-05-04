package com.nat.bless.screens.login.presentation

import androidx.annotation.StringRes
import com.nat.bless.BuildConfig
import com.nat.bless.R

data class LoginState(
    val isLoading: Boolean = false,
    val navigateToHome: Boolean = false,
    val errorMessage: String? = null,

    val mobile: String? = null,
    val password: String? = null,
    val lat: String? = null,
    val lang: String? = null,

    val isValidMobile: Boolean = true,
    @param:StringRes val mobileValidationMessage: Int = R.string.please_fill_mobile,

    val isValidPassword: Boolean = true,
    @param:StringRes val passwordValidationMessage: Int = R.string.please_fill_password
)

// 01090639351
// 1234
fun defaultLoginState() = LoginState(
    isLoading = false,
    navigateToHome = false,
    errorMessage = "",
    mobile = if (BuildConfig.DEBUG) "" else "",
    password = if (BuildConfig.DEBUG) "" else "",
    isValidMobile = true,
    mobileValidationMessage = R.string.please_fill_mobile,
    isValidPassword = true,
    passwordValidationMessage = R.string.please_fill_password
)
