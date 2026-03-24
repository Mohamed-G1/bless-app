package com.nat.bless.screens.login.data.manager

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import com.nat.bless.base.dataStore.PreferencesKeys
import com.nat.bless.base.dataStore.PreferencesKeys.DETAILS_KEY
import com.nat.bless.base.dataStore.dataStore
import com.nat.bless.screens.login.domain.manager.LoginManager
import com.nat.bless.screens.login.domain.models.Detail
import com.nat.bless.screens.login.domain.models.LoginResponse
import kotlinx.coroutines.flow.first

class LoginManagerImpl(
    private val context: Context
) : LoginManager {
    override suspend fun saveUser(response: LoginResponse) {
        val detailsJson = Gson().toJson(response.bonus_summary.details)

        context.dataStore.edit { settings ->
            settings[PreferencesKeys.isLoggedIn] = true
            settings[PreferencesKeys.token] = response.token
            settings[PreferencesKeys.userName] = response.name
            settings[PreferencesKeys.mobile] = response.mobile
            settings[PreferencesKeys.email] = response.email

            settings[PreferencesKeys.targetLevel] = response.target.target_level
            settings[PreferencesKeys.monthlyTarget] = response.target.monthly_target
            settings[PreferencesKeys.consume] = response.target.consumed
            settings[PreferencesKeys.remain] = response.target.remaining
            settings[PreferencesKeys.achievementRate] = response.target.achievement_rate
            settings[PreferencesKeys.totalThisMonth] = response.bonus_summary.total_this_month
            settings[DETAILS_KEY] = detailsJson

        }


    }
//     fun saveDetails(details: List<Detail>,  prefs: MutablePreferences) {
//        val json = Gson().toJson(details)
//        prefs[DETAILS_KEY] = json
//    }

    override suspend fun clearUser() {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.isLoggedIn] = false
            settings[PreferencesKeys.token] = ""
            settings[PreferencesKeys.userName] = ""
            settings[PreferencesKeys.mobile] = ""
            settings[PreferencesKeys.email] = ""


            settings[PreferencesKeys.targetLevel] = ""
            settings[PreferencesKeys.monthlyTarget] = 0.0
            settings[PreferencesKeys.consume] =0.0
            settings[PreferencesKeys.remain] = 0.0
            settings[PreferencesKeys.achievementRate] = 0.0
            settings[PreferencesKeys.totalThisMonth] = 0.0
            settings[DETAILS_KEY] = listOf<String>().toString()

        }
    }

    override suspend fun saveRouteId(routeId: Int) {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.routeId] = routeId
        }
    }
}