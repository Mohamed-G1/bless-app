package com.siad.stayksa.screens.socialMediaSignIn

import kotlinx.serialization.Serializable

@Serializable
data class UserSocialBody(
    val id: String? = "",
    val userName: String? = "",
    val userEmail: String? = "",
    val phoneNumber: String? = "",
    val token: String? = "",
    val driver : String = "",
    val provider_id : String = "",
)
