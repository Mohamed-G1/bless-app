package com.nat.greco.base.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.nat.greco.base.dataStore.PreferencesKeys
import com.nat.greco.base.dataStore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataSourceImpl(
    private val context: Context

) : LocalDataSource {
    override suspend fun saveWaybillSortValue(filter: String) {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.waybillSortType] = filter
        }
    }

    override suspend fun savePickupSortValue(filter: String) {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.pickupSortType] = filter
        }
    }

    override suspend fun saveSelectedCourierType(courier: String) {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.courierType] = courier
        }
    }

    override suspend fun clearWaybillSortValue() {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.waybillSortType] = ""
        }
    }

    override suspend fun clearPickupSortValue() {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.pickupSortType] = ""
        }
    }

    override suspend fun readWaybillSortValue(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.waybillSortType] ?: "Gamal"
        }
    }

    override suspend fun readPickupSortValue(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.pickupSortType] ?: "Gamal"
        }
    }

    override suspend fun readSelectedCourierType(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.courierType] ?: "Gamal"
        }
    }

    override suspend fun saveWaybillFilterValue(filter: String) {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.waybillFilterType] = filter
        }
    }

    override suspend fun clearWaybillFilterValue() {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.waybillFilterType] = ""
        }
    }

    override suspend fun savePickupFilterValue(filter: String) {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.pickupFilterType] = filter
        }
    }

    override suspend fun clearPickupFilterValue() {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.pickupFilterType] = ""
        }
    }

    override suspend fun readWaybillFilterValue(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.waybillFilterType] ?: ""
        }
    }

    override suspend fun readPickupFilterValue(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.pickupFilterType] ?: ""
        }
    }
}