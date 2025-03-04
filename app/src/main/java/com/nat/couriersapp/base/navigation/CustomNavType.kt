package com.nat.couriersapp.base.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.nat.couriersapp.screens.home.domain.models.HomeModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
object CustomNavType {
    val HomeModel = object : NavType<HomeModel>(
        isNullableAllowed = true
    ) {
        override fun get(bundle: Bundle, key: String): HomeModel? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): HomeModel {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: HomeModel): String {
            return Uri.encode(Json.encodeToString(value = value))
        }

        override fun put(bundle: Bundle, key: String, value: HomeModel) {
            bundle.putString(key, Json.encodeToString(value = value))
        }
    }
}