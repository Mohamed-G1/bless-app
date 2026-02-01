package com.nat.bless.base.local

import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun saveWaybillSortValue(filter: String)
    suspend fun savePickupSortValue(filter: String)

    suspend fun saveSelectedCourierType(courier: String)

    suspend fun clearWaybillSortValue()
    suspend fun clearPickupSortValue()

    suspend fun readWaybillSortValue(): Flow<String>
    suspend fun readPickupSortValue(): Flow<String>

    suspend fun readSelectedCourierType(): Flow<String>

    suspend fun saveWaybillFilterValue(filter: String)
    suspend fun clearWaybillFilterValue()

    suspend fun savePickupFilterValue(filter: String)
    suspend fun clearPickupFilterValue()
    suspend fun readWaybillFilterValue(): Flow<String>
    suspend fun readPickupFilterValue(): Flow<String>
}