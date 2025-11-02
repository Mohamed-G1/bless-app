package com.nat.greco.screens.login.presentation

import android.health.connect.datatypes.AppInfo
import androidx.annotation.StringRes
import com.nat.greco.BuildConfig
import com.nat.greco.R

data class LoginState(
    val isLoading: Boolean = false,
    val navigateToHome: Boolean = false,
    val errorMessage: String ? = null,

    val mobile : String? = null,
    val password: String? = null,
    val lat: String? = null,
    val lang: String? = null,

    val isValidMobile : Boolean = true,
    @StringRes val mobileValidationMessage : Int = R.string.please_fill_mobile,

    val isValidPassword : Boolean = true,
    @StringRes val passwordValidationMessage : Int = R.string.please_fill_password
)

fun defaultLoginState() = LoginState(
    isLoading = false,
    navigateToHome = false,
    errorMessage = "",
    mobile = if (BuildConfig.DEBUG) "01090639351" else "",
    password = if (BuildConfig.DEBUG) "123456" else "",
    isValidMobile = true,
    mobileValidationMessage =  R.string.please_fill_mobile,
    isValidPassword = true,
    passwordValidationMessage = R.string.please_fill_password
)
