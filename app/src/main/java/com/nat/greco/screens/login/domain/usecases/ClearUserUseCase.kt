package com.nat.greco.screens.login.domain.usecases

import com.nat.greco.screens.login.domain.manager.LoginManager

class ClearUserUseCase(
    private val loginManager: LoginManager

) {
    suspend operator fun invoke() {
        loginManager.clearUser()
    }
}