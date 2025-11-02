package com.nat.greco.screens.login.domain.usecases

import com.nat.greco.screens.login.domain.manager.LoginManager
import com.nat.greco.screens.login.domain.models.LoginResponse

class SaveUserUseCase(
    private val loginManager: LoginManager
) {
    suspend operator fun invoke(
        response: LoginResponse
    ) {
        loginManager.saveUser(response)
    }
}