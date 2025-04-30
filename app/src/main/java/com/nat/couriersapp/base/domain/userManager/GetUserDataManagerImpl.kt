package com.nat.couriersapp.base.domain.userManager

import android.content.Context
import com.nat.couriersapp.base.dataStore.PreferencesKeys
import com.nat.couriersapp.base.dataStore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUserDataManagerImpl(
    private val context: Context,

    ) : GetUserDataManager {
    override fun readDeliverStatusId(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.deliverStatusId] ?: "6"
        }
    }

    override fun readDeliverReasonId(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.deliverReasonId] ?: "62"
        }
    }

    override fun readInTransitStatusId(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.inTransitStatusId] ?: "83"
        }
    }

    override fun readInTransitReasonId(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.inTransitReasonId] ?: "145"
        }
    }

    override fun readCourierPickedupStatusId(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.courierPickedupStatusId] ?: "79"
        }
    }

    override fun readCourierPickedupReasonId(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.courierPickedupReasonId] ?: "142"
        }
    }

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

    override fun readUserId(): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.userId] ?: 0
        }
    }

    override fun readRoleId(): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.roleId] ?: 4
        }
    }

    override fun isUserLoggedIn(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.isLoggedIn] ?: false
        }
    }
}