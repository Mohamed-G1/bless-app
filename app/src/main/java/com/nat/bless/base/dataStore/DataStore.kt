package com.nat.bless.base.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

const val USER_SETTINGS = "userSettings"
const val SHOULD_SHOW_ONBOARDING = "isAppOnboarding"
const val IS_LOGGED_IN = "isLoggedIn"
const val TOKEN = "token"
const val REFRESH_TOKEN = "refreshToken"
const val USER_NAME = "userName"
const val USER_ID = "userID"
const val WAYBILL_SORT_TYPE = "waybill_sort_type"
const val PICKUP_SORT_TYPE = "pickup_sort_type"
const val WAYBILL_FILTER_TYPE = "filter_type"
const val PICKUP_FILTER_TYPE = "pickup_type"
const val ROLE_ID = "role_id"
const val MOBILE = "MOBILE"
const val EMAIL = "EMAIL"
const val COURIER_TYPE = "courier"
const val DeliverStatusId = "DeliverStatusId"
const val DeliverReasonId = "DeliverReasonId"
const val InTransitStatusId = "InTransitStatusId"
const val InTransitReasonId = "InTransitReasonId"
const val CourierPickedupStatusId = "CourierPickedupStatusId"
const val CourierPickedupReasonId = "CourierPickedupReasonId"
const val ROUTE_ID = "routeId"
const val target_level = "target_level"
const val monthly_target = "monthly_target"
const val consumed = "consumed"
const val remaining = "remaining"
const val achievement_rate = "achievement_rate"
const val total_this_month = "total_this_month"
const val details = "details"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS)

object PreferencesKeys {
    val shouldShowOnboarding = booleanPreferencesKey(name = SHOULD_SHOW_ONBOARDING)
    val isLoggedIn = booleanPreferencesKey(name = IS_LOGGED_IN)
    val token = stringPreferencesKey(name = TOKEN)
    val mobile = stringPreferencesKey(name = MOBILE)
    val email = stringPreferencesKey(name = EMAIL)
    val routeId = intPreferencesKey(name = ROUTE_ID)
    val refreshToken = stringPreferencesKey(name = REFRESH_TOKEN)
    val userName = stringPreferencesKey(name = USER_NAME)
    val courierType = stringPreferencesKey(name = COURIER_TYPE)
    val waybillSortType = stringPreferencesKey(name = WAYBILL_SORT_TYPE)
    val pickupSortType = stringPreferencesKey(name = PICKUP_SORT_TYPE)
    val waybillFilterType = stringPreferencesKey(name = WAYBILL_FILTER_TYPE)
    val pickupFilterType = stringPreferencesKey(name = PICKUP_FILTER_TYPE)
    val userId = intPreferencesKey(name = USER_ID)
    val roleId = intPreferencesKey(name = ROLE_ID)

    val deliverStatusId = stringPreferencesKey(name = DeliverStatusId)
    val deliverReasonId = stringPreferencesKey(name = DeliverReasonId)
    val inTransitStatusId = stringPreferencesKey(name = InTransitStatusId)
    val inTransitReasonId = stringPreferencesKey(name = InTransitReasonId)
    val courierPickedupStatusId = stringPreferencesKey(name = CourierPickedupStatusId)
    val courierPickedupReasonId = stringPreferencesKey(name = CourierPickedupReasonId)

    val targetLevel = stringPreferencesKey(name = target_level)
    val monthlyTarget = doublePreferencesKey(name = monthly_target)
    val consume = doublePreferencesKey(name = consumed)
    val remain = doublePreferencesKey(name = remaining)
    val achievementRate = doublePreferencesKey(name = achievement_rate)
    val totalThisMonth = doublePreferencesKey(name = total_this_month)
    val DETAILS_KEY = stringPreferencesKey("details")
}