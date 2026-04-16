package com.nat.bless.screens.category.presentation

import com.nat.bless.screens.category.domain.models.CategoryResponse

data class CategoryState(
    val isLoading: Boolean = false,
    val message: String = "",
    val data: List<CategoryResponse> = listOf()
)