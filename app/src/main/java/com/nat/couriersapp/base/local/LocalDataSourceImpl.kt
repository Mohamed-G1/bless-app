package com.nat.couriersapp.base.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.nat.couriersapp.base.dataStore.PreferencesKeys
import com.nat.couriersapp.base.dataStore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataSourceImpl(
    private val context: Context

) : LocalDataSource {
    override suspend fun saveSortValue(filter: String) {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.sortType] = filter
        }
    }

    override suspend fun clearSortValue() {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.sortType] = ""
        }
    }

    override suspend fun readSortValue(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.sortType] ?: "Gamal"
        }
    }

    override suspend fun saveFilterValue(filter: String) {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.filterType] = filter
        }
    }

    override suspend fun clearFilterValue() {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.filterType] = ""
        }
    }

    override suspend fun readFilterValue(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.filterType] ?: ""
        }
    }
}