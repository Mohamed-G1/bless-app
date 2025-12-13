package com.nat.greco.screens.collect.presenation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.greco.R
import com.nat.greco.base.ui.appButton.AppButton
import com.nat.greco.base.ui.appLoading.FullLoading
import com.nat.greco.base.ui.textField.AppTextField
import com.nat.greco.base.ui.toast.ShowToast
import com.nat.greco.screens.collect.domain.models.CollectResponse
import com.nat.greco.ui.theme.CompactTypography
import com.nat.greco.ui.theme.MediumBlue
import com.nat.greco.ui.theme.MediumGray

@Composable
fun CollectScreen(
    state: CollectState,
    events: ((CollectEvents) -> Unit)? = null,
    customerId: Int,
    onBackClicked: (() -> Unit)? = null,
    popBack: (() -> Unit)? = null
) {

    LaunchedEffect(Unit) {
        events?.invoke(CollectEvents.CustomerIdChanged(customerId))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp, horizontal = 16.dp)

    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "تحصيل",
                    style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
                )

                IconButton(onClick = { onBackClicked?.invoke() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = null
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "ادخل المبلغ",
                style = CompactTypography.headlineMedium.copy(fontSize = 16.sp)
            )
            Spacer(Modifier.height(8.dp))
            Amount(
                state = state,
                events = events
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "ادخل ملاحظات",
                style = CompactTypography.headlineMedium.copy(fontSize = 16.sp)
            )
            Spacer(Modifier.height(8.dp))

            Notes(
                state = state,
                events = events
            )



            Spacer(Modifier.height(24.dp))


            Text(
                "اختر طريقة تحصيل",
                style = CompactTypography.headlineMedium.copy(fontSize = 16.sp)
            )
            Spacer(Modifier.height(8.dp))
            LazyColumn {
                items(state.model) { item ->
                    JournalItem(
                        item = item,
                        alreadySelected = state.journalId,
                        onClick = { id ->
                            events?.invoke(CollectEvents.JournalIdChanged(id))
                        }
                    )
                }
            }
            Spacer(Modifier.height(8.dp))

            if (!state.isValidJournalId) {
                Text(
                    " برجاء اختيار طريقة تحصيل",
                    style = CompactTypography.bodyMedium.copy(color = Color.Red)
                )
            }
        }


        Spacer(Modifier.height(16.dp))

        AppButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = "اضافة",
            onClick = {
                events?.invoke(CollectEvents.CreatePayment)
            }
        )
    }

    if (state.error.isNotEmpty()) {
        ShowToast(state.error)
    }

    if (state.isLoading) {
        FullLoading()
    }

    if (state.navigateBack) {
        popBack?.invoke()
    }


}

@Composable
fun JournalItem(
    item: CollectResponse,
    alreadySelected: Int,
    onClick: (Int) -> Unit,
) {
    var selectedId by remember {
        mutableIntStateOf(alreadySelected)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = item.name,
            style = CompactTypography.labelMedium.copy(color = Color.Gray)
        )

        RadioButton(
            selected = selectedId == item.id,
            onClick = {
                selectedId = item.id
                onClick(item.id)
            },
            colors = RadioButtonDefaults.colors(
                selectedColor = MediumBlue,
                unselectedColor = MediumBlue
            )
        )
    }

}


@Composable
private fun Notes(
    state: CollectState,
    events: ((CollectEvents) -> Unit)? = null,
) {
    AppTextField(
        modifier = Modifier.fillMaxWidth().height(80.dp),
        label = "ملاحظات",
        maxLines = 3,
        value = state.notes,
        onValueChange = {
            events?.invoke(CollectEvents.NoteChanged(it))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        borderUnFocusColor = MediumGray
    )
}

@Composable
private fun Amount(
    state: CollectState,
    events: ((CollectEvents) -> Unit)? = null,
) {
    var text by remember { mutableStateOf(if (state.amount == 0.0) "" else state.amount.toString()) }

    LaunchedEffect(state.amount) {
        val amtText = if (state.amount == 0.0) "" else state.amount.toString()
        if (amtText != text) text = amtText
    }

    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        label = "المبلغ",
        placeholder = "المبلغ",
        isError = !state.isValidAmount,
        errorMessage = if (state.isValidAmount) null else "الرجاء ادخال المبلغ بشكل صحيح",
        value = text,
        onValueChange = { raw ->
            // allow only digits and a single decimal point, prevent leading dot
            val filtered = raw.filter { c -> c.isDigit() || c == '.' }
            val sanitized = if (filtered.count { it == '.' } > 1) {
                val firstDot = filtered.indexOf('.')
                val before = filtered.substring(0, firstDot + 1)
                val after = filtered.substring(firstDot + 1).replace(".", "")
                before + after
            } else {
                filtered
            }
            val final = if (sanitized.startsWith('.')) "0$sanitized" else sanitized
            text = final
            events?.invoke(CollectEvents.AmountChanged(final.toDoubleOrNull() ?: 0.0))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
        borderUnFocusColor = MediumGray
    )
}

@Preview(showBackground = true)
@Composable
private fun CollectScreenPreview() {
    CollectScreen(state = CollectState(), customerId = 0)
}