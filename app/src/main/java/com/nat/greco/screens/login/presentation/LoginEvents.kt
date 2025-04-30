package com.nat.greco.screens.login.presentation

sealed class LoginEvents {
    data object SubmitUser : LoginEvents()
    data class UserNameChanged(val userName: String) : LoginEvents()
    data class PasswordChanged(val password: String) : LoginEvents()
    data object NavigationComplete : LoginEvents()
}