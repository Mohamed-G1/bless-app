package com.nat.couriersapp.base.domain.userManager

import kotlinx.coroutines.flow.Flow

interface GetUserDataManager {
    fun readToken(): Flow<String>
    fun readName(): Flow<String>
    fun readUserId(): Flow<Int>
    fun isUserLoggedIn() : Flow<Boolean>
}