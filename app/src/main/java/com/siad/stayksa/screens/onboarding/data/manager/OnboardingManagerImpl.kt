package com.siad.stayksa.screens.onboarding.data.manager

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.siad.stayksa.base.dataStore.PreferencesKeys
import com.siad.stayksa.base.dataStore.dataStore
import com.siad.stayksa.screens.onboarding.domain.manager.OnboardingManager

class OnboardingManagerImpl(
    private val context: Context
) : OnboardingManager {
    override suspend fun saveOnboarding() {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.shouldShowOnboarding] = false
        }
    }


}