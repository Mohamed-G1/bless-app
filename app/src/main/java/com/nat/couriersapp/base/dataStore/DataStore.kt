package com.nat.couriersapp.base.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

const val USER_SETTINGS = "userSettings"
const val SHOULD_SHOW_ONBOARDING = "isAppOnboarding"
const val IS_LOGGED_IN = "isLoggedIn"
const val TOKEN = "token"
const val REFRESH_TOKEN = "refreshToken"
const val IS_ACTIVE_USER = "isActiveUser"
const val USER_NAME = "userName"
const val USER_EMAIL = "userEMAIL"
const val USER_PHONE= "userPHONE"
const val USER_LANGUAGE = "userLanguage"
const val USER_LANGUAGE_FLAG = "userLanguageFlag"
const val USER_LANGUAGE_NAME = "userLanguageName"
const val DEVICE_ID = "deviceId"

// Filter inputs
const val TYPE_PLACE = "type_place"
const val STAR_RATING = "star_rating"
const val GUEST_REVIEW_SCORE = "guest_score"
const val FREE_CANCEL = "free_cancel"
const val BENEFITS = "benefits"
const val FACILITIES = "facilities "
const val HOUSE_RULES = "house_rules"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS)

object PreferencesKeys {
    val shouldShowOnboarding = booleanPreferencesKey(name = SHOULD_SHOW_ONBOARDING)
    val isLoggedIn = booleanPreferencesKey(name = IS_LOGGED_IN)
    val token = stringPreferencesKey(name = TOKEN)
    val refreshToken = stringPreferencesKey(name = REFRESH_TOKEN)
//    val isActiveUser = booleanPreferencesKey(name = IS_ACTIVE_USER)
    val userName = stringPreferencesKey(name = USER_NAME)
    val userEmail = stringPreferencesKey(name = USER_EMAIL)
    val userPhone = stringPreferencesKey(name = USER_PHONE)
    val userLanguage = stringPreferencesKey(name = USER_LANGUAGE)
    val userLanguageFlag = stringPreferencesKey(name = USER_LANGUAGE_FLAG)
    val userLanguageName = stringPreferencesKey(name = USER_LANGUAGE_NAME)
    val deviceId = stringPreferencesKey(name = DEVICE_ID)


    val typePlace = intPreferencesKey(name = TYPE_PLACE)
    val starRating = intPreferencesKey(name = STAR_RATING)
    val guestReviewScore = intPreferencesKey(name = GUEST_REVIEW_SCORE)
    val isFreeCancel = booleanPreferencesKey(name = FREE_CANCEL )
    val benefitsList = stringSetPreferencesKey(name = BENEFITS )
    val facilitiesList = stringSetPreferencesKey(name = FACILITIES )
    val houseRulesList = stringSetPreferencesKey(name = HOUSE_RULES )
}