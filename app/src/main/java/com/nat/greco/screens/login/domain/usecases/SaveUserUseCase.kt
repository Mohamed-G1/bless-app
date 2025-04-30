package com.nat.greco.screens.login.domain.usecases

import com.nat.greco.screens.login.domain.manager.LoginManager
import com.nat.greco.screens.login.domain.models.UserResponse

class SaveUserUseCase(
    private val loginManager: LoginManager
) {
    suspend operator fun invoke(
        response: UserResponse
    ) {
        loginManager.saveUser(response)
    }
}