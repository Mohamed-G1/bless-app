package com.nat.greco.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nat.greco.base.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    fun <T> executeSuspend(
        block: suspend () -> Resource<T>,
        onSuccess: (T?) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        viewModelScope.launch {
            val result = block()
            when (result) {
                is Resource.Success -> onSuccess(result.data)
                is Resource.Error -> onFailure(result.message)
                is Resource.Loading -> Unit // Shouldn't happen in a one-time operation
            }
        }
    }

    // This function for flow operations
    fun <T> executeFlow(
        block: suspend () -> Flow<Resource<T>>,
        onLoading: (Boolean) -> Unit,
        onSuccess: (T?) -> Unit,
        onFailure: (String, Int) -> Unit,
    ) {
        viewModelScope.launch {
            block().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        onLoading(true)
                    }

                    is Resource.Success -> {
                        onSuccess(result.data)
                        onLoading(false)
                    }

                    is Resource.Error -> {
                        onFailure(result.message, result.code ?: 200)
                        onLoading(false)
                    }
                }
            }
        }
    }
}