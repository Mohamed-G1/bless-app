package com.nat.couriersapp.base.local

import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun saveSortValue(filter: String)
    suspend fun clearSortValue()
    suspend fun readSortValue(): Flow<String>

    suspend fun saveFilterValue(filter: String)
    suspend fun clearFilterValue()
    suspend fun readFilterValue(): Flow<String>
}