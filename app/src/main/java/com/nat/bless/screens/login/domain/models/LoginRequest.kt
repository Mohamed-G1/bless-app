package com.nat.bless.screens.login.domain.models

data class LoginRequest(
    val mobile : String,
    val password : String,
    val fcm_token : String,
    val location_length : String,
    val location_circles : String,
)
