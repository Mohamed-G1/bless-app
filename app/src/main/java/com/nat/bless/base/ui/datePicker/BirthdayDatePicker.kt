package com.nat.bless.application.base.presentation.datePicker

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.nat.bless.R
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.utils.formattedDateFromMillis

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthDatePicker(
    onDateSelected: (String?) -> Unit,
    onDismiss: () -> Unit,
    initialSelectedDateMillis: Long? = null,
) {

    val datePickerState =
        rememberDatePickerState(
            initialSelectedDateMillis = initialSelectedDateMillis
        )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(
                    datePickerState.selectedDateMillis?.formattedDateFromMillis().orEmpty()
                )
                onDismiss()
            }) {
                Text(stringResource(R.string.ok), style = CompactTypography.labelMedium)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel), style = CompactTypography.labelMedium)
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}