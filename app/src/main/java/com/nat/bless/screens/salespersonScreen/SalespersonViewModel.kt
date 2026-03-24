package com.nat.bless.screens.salespersonScreen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.nat.bless.base.BaseViewModel
import com.nat.bless.base.domain.userManager.GetUserDataManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SalespersonViewModel(
    val getUserDataManager: GetUserDataManager
) : BaseViewModel() {
    private val _state = MutableStateFlow(SalespersonState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { it.copy(
                name = getUserDataManager.readName().first(),
                totalBonus = getUserDataManager.readTotalThisMonth().first(),
                achievementRate = getUserDataManager.readAchievementRate().first(),
                consumed = getUserDataManager.readConsume().first(),
                monthlyTarget = getUserDataManager.readMonthlyTarget().first(),
                remaining = getUserDataManager.readRemain().first(),
                targetLevel = getUserDataManager.readTargetLevel().first(),
                details =getUserDataManager.readDetails().first(),
            ) }

        }
    }

}