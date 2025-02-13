package com.siad.stayksa.base.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siad.stayksa.base.network.Resource
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    // This function for one-time operations
    private fun <T> executeSuspend(
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
    private fun <T> executeFlow(
        block: suspend () -> Resource<T>,
        onLoading: (Boolean) -> Unit,
        onSuccess: (T?) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        viewModelScope.launch {
            val result = block()
            when (result) {
                is Resource.Loading -> {
                    onLoading(true)
                }

                is Resource.Success -> {
                    onSuccess(result.data)
                    onLoading(false)
                }

                is Resource.Error -> {
                    onFailure(result.message)
                    onLoading(false)
                }
            }
        }
    }
}