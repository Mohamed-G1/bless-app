package com.siad.stayksa.screens.splash.data.manager

import android.content.Context
import com.siad.stayksa.base.dataStore.PreferencesKeys
import com.siad.stayksa.base.dataStore.dataStore
import com.siad.stayksa.screens.splash.manager.SplashManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SplashManagerImpl(
    private val context: Context
) : SplashManager {

    override fun readOnboarding(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.shouldShowOnboarding] ?: true
        }
    }

    override fun isLoggedIn(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.isLoggedIn] ?: false
        }
    }
}