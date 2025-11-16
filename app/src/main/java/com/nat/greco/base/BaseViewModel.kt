package com.nat.greco.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nat.greco.base.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    inline fun <reified T> executeSuspend(
        crossinline block: suspend () -> Resource<T>,
        crossinline onSuccess: (T?) -> Unit,
        crossinline onFailure: (String) -> Unit,
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
    inline fun <reified T> executeFlow(
        crossinline block: suspend () -> Flow<Resource<T>>,
        crossinline onLoading: (Boolean) -> Unit,
        crossinline onSuccess: (T?) -> Unit,
        crossinline onFailure: (String, Int) -> Unit,
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