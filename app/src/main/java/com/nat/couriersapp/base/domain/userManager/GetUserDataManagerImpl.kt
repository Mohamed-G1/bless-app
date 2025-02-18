package com.nat.couriersapp.base.domain.userManager

import android.content.Context
import com.nat.couriersapp.base.dataStore.PreferencesKeys
import com.nat.couriersapp.base.dataStore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUserDataManagerImpl(
    private val context: Context,

    ) : GetUserDataManager {
    override fun readToken(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.token] ?: ""
        }

    }

    override fun readName(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.userName] ?: "Gamal"
        }

    }

    override fun isUserLoggedIn(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.isLoggedIn] ?: false
        }
    }
}