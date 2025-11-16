package com.nat.greco.screens.stocks.peresentation

import androidx.lifecycle.viewModelScope
import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseViewModel
import com.nat.greco.base.domain.userManager.GetUserDataManager
import com.nat.greco.screens.addNewOrders.domain.usecases.GetProductsListUseCase
import com.nat.greco.screens.addNewOrders.models.NewProductRequest
import com.nat.greco.screens.stocks.domain.usecases.GetReturnsStockUseCase
import com.nat.greco.screens.stocks.domain.usecases.SearchReturnsUseCase
import com.nat.greco.screens.stocks.domain.usecases.SearchStockUseCase
import com.nat.greco.screens.stocks.models.SearchRequest
import com.nat.greco.screens.stocks.models.StockRequest
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StockViewModel(
    private val getUserDataManager: GetUserDataManager,
    private val getReturnsStockUseCase: GetReturnsStockUseCase,
    private val getProductsListUseCase: GetProductsListUseCase,
    private val searchStockUseCase: SearchStockUseCase,
    private val searchReturnsUseCase: SearchReturnsUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(StockState())
    val state = _state.asStateFlow()


    private val _intentChannel = Channel<StockEvents>(Channel.UNLIMITED)
    fun sendEvent(intent: StockEvents) {
        viewModelScope.launch {
            _intentChannel.send(intent)
        }
    }

    init {
        _state.update { it.copy(error = "") }

        callStockApi()
        processEvents()
    }

    private fun processEvents() {
        viewModelScope.launch {
            _intentChannel.consumeAsFlow().collect { event ->
                when (event) {
                    is StockEvents.GetReturnsStock -> {
                        callReturnsStockApi()
                    }

                    is StockEvents.GetStockList -> {
                        callStockApi()
                    }

                    is StockEvents.SearchStockQueryChanged -> {
                        _state.update { it.copy(stockSearchQuery = event.query) }


                    }

                    is StockEvents.SearchReturnsQueryChanged -> {
                        _state.update { it.copy(returnsSearchQuery = event.query) }

                    }

                    is StockEvents.ClearStockSearchQuery -> {
                        _state.update { it.copy(stockSearchQuery = "") }
                        callStockApi()
                    }

                    is StockEvents.ClearReturnsSearchQuery -> {
                        _state.update { it.copy(returnsSearchQuery = "") }
                        callReturnsStockApi()
                    }

                    is StockEvents.TriggerSearchStock -> {
                        callSearchStockApi()
                    }

                    is StockEvents.TriggerSearchReturns -> {
                        callSearchReturnsApi()
                    }
                }
            }
        }
    }

    private fun callSearchStockApi() {
        _state.update { it.copy(error = "") }
        executeFlow(block = {
            searchStockUseCase.invoke(
                request = BaseRequest(
                    params = SearchRequest(
                        token = getUserDataManager.readToken().first(),
                        query = state.value.stockSearchQuery,
                        location = "stock"
                    )
                )
            )
        }, onLoading = { value ->
            _state.update { it.copy(isLoading = value) }

        }, onSuccess = { result ->
            if (result?.result?.data?.isEmpty() == true){
                _state.update { it.copy(error = result.result.message) }

            }else{
                _state.update { it.copy(stockList = result?.result?.data ?: listOf()) }
            }

        }, onFailure = { error, _ ->
            _state.update { it.copy(error = error) }

        }

        )
    }

    private fun callSearchReturnsApi() {
        _state.update { it.copy(error = "") }
        executeFlow(block = {
            searchReturnsUseCase.invoke(
                request = BaseRequest(
                    params = SearchRequest(
                        token = getUserDataManager.readToken().first(),
                        query = state.value.returnsSearchQuery,
                        location = "return"
                    )
                )
            )
        }, onLoading = { value ->
            _state.update { it.copy(isLoading = value) }

        }, onSuccess = { result ->
            if (result?.result?.data?.isEmpty() == true){
                _state.update { it.copy(error = result.result.message) }

            }else{
                _state.update { it.copy(returnsList = result?.result?.data ?: listOf()) }
            }

        }, onFailure = { error, _ ->
            _state.update { it.copy(error = error) }
        }

        )
    }

    private fun callStockApi() {
        executeFlow(block = {
            getProductsListUseCase.invoke(
                request = BaseRequest(
                    params = NewProductRequest(
                        token = getUserDataManager.readToken().first(),
                    )
                )
            )
        }, onLoading = { value ->
            _state.update { it.copy(isLoading = value) }

        }, onSuccess = { result ->
            _state.update { it.copy(stockList = result?.result?.data ?: listOf()) }

        }, onFailure = { error, _ ->
            _state.update { it.copy(error = error) }
        }

        )
    }


    private fun callReturnsStockApi() {
        executeFlow(block = {
            getReturnsStockUseCase.invoke(
                request = BaseRequest(
                    params = StockRequest(
                        token = getUserDataManager.readToken().first(),
                    )
                )
            )
        }, onLoading = { value ->
            _state.update { it.copy(isLoading = value) }

        }, onSuccess = { result ->
            if (result?.result?.data?.isEmpty() == true){
                _state.update { it.copy(error = result.result.message) }

            }else{
                _state.update { it.copy(returnsList = result?.result?.data ?: listOf()) }
            }

        }, onFailure = { error, _ ->
            _state.update { it.copy(error = error) }
        }

        )
    }
}