package com.nat.bless.screens.category.presentation

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseViewModel
import com.nat.bless.base.domain.userManager.GetUserDataManager
import com.nat.bless.screens.category.domain.models.CategoryRequest
import com.nat.bless.screens.category.domain.useCases.GetCategoryDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

class CategoryViewModel(
    private val getUserDataManager: GetUserDataManager,
    private val getCategoryDataUseCase: GetCategoryDataUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(CategoryState())
    val state = _state.asStateFlow()

    init {
        getCategories()
    }


    private fun getCategories() {
        _state.update { it.copy(message = "") }
        executeFlow(
            block = {
                getCategoryDataUseCase.invoke(
                    request = BaseRequest(
                        params = CategoryRequest(
                            token = getUserDataManager.readToken().first()
                        )
                    )
                )
            },
            onLoading = { value ->
                _state.update { it.copy(isLoading = value) }

            },
            onSuccess = { result ->
                _state.update { it.copy(data = result?.result?.data ?: listOf()) }
            },
            onFailure = { error, _ ->
                _state.update { it.copy(message = error) }
            }
        )
    }

}