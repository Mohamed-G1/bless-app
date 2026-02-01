package com.nat.bless.screens.login.domain.models

data class LoginResponse(
    val email: String,
    val mobile: String,
    val name: String,
    val token: String
)