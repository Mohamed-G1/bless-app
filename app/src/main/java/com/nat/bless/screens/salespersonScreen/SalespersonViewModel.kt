package com.nat.bless.screens.salespersonScreen

import androidx.lifecycle.viewModelScope
import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseViewModel
import com.nat.bless.base.domain.userManager.GetUserDataManager
import com.nat.bless.screens.salespersonBonusScreen.BonusDetailsRequest
import com.nat.bless.screens.salespersonBonusScreen.SalespersonBonusRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SalespersonViewModel(
    val getUserDataManager: GetUserDataManager,
    val salespersonBonusRepository: SalespersonBonusRepository
) : BaseViewModel() {
    private val _state = MutableStateFlow(SalespersonState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    name = getUserDataManager.readName().first(),
//                    totalBonus = getUserDataManager.readTotalThisMonth().first(),
//                    achievementRate = getUserDataManager.readAchievementRate().first(),
//                    consumed = getUserDataManager.readConsume().first(),
//                    monthlyTarget = getUserDataManager.readMonthlyTarget().first(),
//                    remaining = getUserDataManager.readRemain().first(),
//                    targetLevel = getUserDataManager.readTargetLevel().first(),
//                    details = getUserDataManager.readDetails().first(),
                )
            }

        }

        getSalespersonTargetDetails()
    }


    private fun getSalespersonTargetDetails() {
        executeFlow(
            block = {
                salespersonBonusRepository.getSalespersonTargetDetails(
                    request = BaseRequest(
                        params = SalespersonTargetRequest(
                            token = getUserDataManager.readToken().first(),
                        )
                    )
                )
            },
            onLoading = { isLoading ->
                _state.value = _state.value.copy(isLoading = isLoading)
            },
            onSuccess = { response ->
                response?.result?.data.let {
                    _state.value = _state.value.copy(model = it)
                }
            },
            onFailure = { message, code ->
                _state.value = _state.value.copy(error = message)
            }
        )
    }

}