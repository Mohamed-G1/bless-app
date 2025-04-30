package com.nat.greco.base.domain.userManager

import kotlinx.coroutines.flow.Flow

interface GetUserDataManager {
    fun readDeliverStatusId(): Flow<String>
    fun readDeliverReasonId(): Flow<String>
    fun readInTransitStatusId(): Flow<String>
    fun readInTransitReasonId(): Flow<String>
    fun readCourierPickedupStatusId(): Flow<String>
    fun readCourierPickedupReasonId(): Flow<String>

    fun readToken(): Flow<String>
    fun readName(): Flow<String>
    fun readUserId(): Flow<Int>
    fun readRoleId(): Flow<Int>
    fun isUserLoggedIn() : Flow<Boolean>
}