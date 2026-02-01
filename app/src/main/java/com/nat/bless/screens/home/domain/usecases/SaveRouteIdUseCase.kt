package com.nat.bless.screens.home.domain.usecases

import com.nat.bless.screens.login.domain.manager.LoginManager

class SaveRouteIdUseCase(

    private val loginManager: LoginManager
) {
    suspend operator fun invoke(
        id: Int
    ) {
        loginManager.saveRouteId(id)
    }
}