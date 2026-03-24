package com.nat.bless.base.domain.userManager

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nat.bless.base.dataStore.PreferencesKeys
import com.nat.bless.base.dataStore.PreferencesKeys.DETAILS_KEY
import com.nat.bless.base.dataStore.dataStore
import com.nat.bless.screens.login.domain.models.Detail
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

    override suspend fun readRouteId(): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.routeId] ?: 0
        }
    }

    override suspend fun readTargetLevel(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.targetLevel] ?: "Gamal"
        }
    }

    override suspend fun readMonthlyTarget(): Flow<Double> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.monthlyTarget] ?: 0.0
        }
    }

    override suspend fun readConsume(): Flow<Double> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.consume] ?: 0.0
        }
    }

    override suspend fun readRemain(): Flow<Double> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.remain] ?: 0.0
        }
    }

    override suspend fun readAchievementRate(): Flow<Double> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.achievementRate] ?: 0.0
        }
    }

    override suspend fun readTotalThisMonth(): Flow<Double> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.totalThisMonth] ?: 0.0
        }
    }

    override suspend fun readDetails(): Flow<List<Detail>> = context.dataStore.data.map { prefs ->
        val json = prefs[DETAILS_KEY] ?: return@map emptyList()
        val type = object : TypeToken<List<Detail>>() {}.type
        Gson().fromJson(json, type)
    }
}