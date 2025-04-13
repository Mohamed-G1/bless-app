package com.nat.couriersapp.screens.login.data.manager

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.nat.couriersapp.base.dataStore.PreferencesKeys
import com.nat.couriersapp.base.dataStore.dataStore
import com.nat.couriersapp.screens.login.domain.manager.LoginManager
import com.nat.couriersapp.screens.login.domain.models.UserResponse

class LoginManagerImpl(
    private val context: Context
) : LoginManager {
    override suspend fun saveUser(response: UserResponse) {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.isLoggedIn] = true
            settings[PreferencesKeys.token] = response.obj?.token ?: ""
            settings[PreferencesKeys.userName] = response.obj?.userName ?: ""
            settings[PreferencesKeys.userId] = response.obj?.userId ?: 0
            settings[PreferencesKeys.roleId] = response.obj?.roleId ?: 0
        }
    }


    override suspend fun clearUser() {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.isLoggedIn] = false
            settings[PreferencesKeys.token] = ""
            settings[PreferencesKeys.userName] = ""
            settings[PreferencesKeys.userId] = 0
            settings[PreferencesKeys.roleId] = 0
            settings[PreferencesKeys.courierType] = ""
            settings[PreferencesKeys.pickupFilterType] = ""
            settings[PreferencesKeys.waybillFilterType] = ""
            settings[PreferencesKeys.waybillSortType] = ""
            settings[PreferencesKeys.waybillSortType] = ""

        }
    }

}