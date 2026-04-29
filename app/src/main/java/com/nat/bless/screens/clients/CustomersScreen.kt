package com.nat.bless.screens.clients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.bless.base.ui.appLoading.FullLoading
import com.nat.bless.base.ui.textField.AppTextField
import com.nat.bless.base.ui.toast.ShowToast
import com.nat.bless.screens.stocks.peresentation.StockEvents
import com.nat.bless.screens.stocks.peresentation.StockState
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.ui.theme.MediumGray

@Composable
fun CustomersScreen(
    state: CutomersState,
    events: ((CustomersEvents) -> Unit)? = null,

    ) {

    LaunchedEffect(Unit) {
        events?.invoke(CustomersEvents.FetchCustomers)
    }
    // Local search query state
    var searchQuery by remember { mutableStateOf("") }

    // Filtered list - recomputed only when query or customers change
    val filteredCustomers = remember(searchQuery, state.customers) {
        if (searchQuery.isBlank()) {
            state.customers
        } else {
            state.customers.filter { customer ->
                customer.name.contains(searchQuery, ignoreCase = true)
                // Add more fields if needed, e.g.:
                // || customer.phone.contains(searchQuery, ignoreCase = true)
                // || customer.code.contains(searchQuery, ignoreCase = true)
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 16.dp),

        ) {

        Text(
            "العملاء",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
        )
        Spacer(Modifier.height(16.dp))

        ClientsSearchField(
            query = searchQuery,
            onQueryChange = { searchQuery = it }
        )


        Spacer(Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items = filteredCustomers) { item ->
                CustomerItem(
                    item = item
                )
            }
        }
    }

    if (state.error.isNotEmpty()) {
        ShowToast(state.error)
    }

    if (state.isLoading) {
        FullLoading()
    }
}

@Composable
private fun ClientsSearchField(
    query: String,
    onQueryChange: (String) -> Unit
) {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "بحث",
        label = "بحث",
        isError = false,
        errorMessage = null,
        value = query,
        onValueChange = onQueryChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { /* keyboard will close automatically */ }
        ),
        trailingCompose = {
            if (query.isEmpty()) {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = MediumGray,
                    contentDescription = ""
                )
            } else {
                IconButton(
                    onClick = { onQueryChange("") }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = MediumGray,
                        contentDescription = ""
                    )
                }
            }
        }
    )
}


@Preview
@Composable
private fun ClientsScreenP() {
    CustomersScreen(CutomersState())
}