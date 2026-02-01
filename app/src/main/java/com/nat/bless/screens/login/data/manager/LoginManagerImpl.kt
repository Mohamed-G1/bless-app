package com.nat.bless.screens.login.data.manager

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.nat.bless.base.dataStore.PreferencesKeys
import com.nat.bless.base.dataStore.dataStore
import com.nat.bless.screens.login.domain.manager.LoginManager
import com.nat.bless.screens.login.domain.models.LoginResponse

class LoginManagerImpl(
    private val context: Context
) : LoginManager {
    override suspend fun saveUser(response: LoginResponse) {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.isLoggedIn] = true
            settings[PreferencesKeys.token] = response.token
            settings[PreferencesKeys.userName] = response.name
            settings[PreferencesKeys.mobile] = response.mobile
            settings[PreferencesKeys.email] = response.email
        }
    }


    override suspend fun clearUser() {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.isLoggedIn] = false
            settings[PreferencesKeys.token] = ""
            settings[PreferencesKeys.userName] = ""
            settings[PreferencesKeys.mobile] = ""
            settings[PreferencesKeys.email] = ""

        }
    }

    override suspend fun saveRouteId(routeId: Int) {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.routeId] = routeId
        }
    }
}