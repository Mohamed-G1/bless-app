package com.nat.bless.base.domain.userManager

import com.nat.bless.screens.login.domain.models.Detail
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


    suspend fun readRouteId(): Flow<Int>

    suspend fun readTargetLevel(): Flow<String>
    suspend fun readMonthlyTarget(): Flow<Double>
    suspend fun readConsume(): Flow<Double>
    suspend fun readRemain(): Flow<Double>
    suspend fun readAchievementRate(): Flow<Double>
    suspend fun readTotalThisMonth(): Flow<Double>
    suspend fun readDetails(): Flow<List<Detail>>
}