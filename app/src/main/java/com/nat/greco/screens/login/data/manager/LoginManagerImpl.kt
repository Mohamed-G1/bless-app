package com.nat.greco.screens.login.data.manager

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.nat.greco.base.dataStore.PreferencesKeys
import com.nat.greco.base.dataStore.dataStore
import com.nat.greco.screens.login.domain.manager.LoginManager
import com.nat.greco.screens.login.domain.models.UserResponse

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

            settings[PreferencesKeys.deliverStatusId] = response.obj?.DeliverStatusId ?: "6"
            settings[PreferencesKeys.deliverReasonId] = response.obj?.DeliverReasonId ?: "62"
            settings[PreferencesKeys.inTransitStatusId] = response.obj?.InTransitStatusId ?: "83"
            settings[PreferencesKeys.inTransitReasonId] = response.obj?.InTransitReasonId ?: "145"
            settings[PreferencesKeys.courierPickedupStatusId] =
                response.obj?.CourierPickedupStatusId ?: "79"
            settings[PreferencesKeys.courierPickedupReasonId] =
                response.obj?.CourierPickedupReasonId ?: "142"
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