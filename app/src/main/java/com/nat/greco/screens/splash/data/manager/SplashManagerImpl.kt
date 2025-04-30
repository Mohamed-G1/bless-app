package com.nat.greco.screens.splash.data.manager

import android.content.Context
import com.nat.greco.base.dataStore.PreferencesKeys
import com.nat.greco.base.dataStore.dataStore
import com.nat.greco.screens.splash.manager.SplashManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SplashManagerImpl(
    private val context: Context
) : SplashManager {

    override fun isLoggedIn(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.isLoggedIn] ?: false
        }
    }
}