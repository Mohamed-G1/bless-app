package com.nat.couriersapp.screens.login.domain.usecases

import com.nat.couriersapp.screens.login.domain.manager.LoginManager

class ClearUserUseCase(
    private val loginManager: LoginManager

) {
    suspend operator fun invoke() {
        loginManager.clearUser()
    }
}