package com.siad.stayksa.application.base.presentation.datePicker

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.siad.stayksa.R
import com.siad.stayksa.ui.theme.CompactTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePicker(
    onDateSelected: (String?) -> Unit,
    onDismiss: () -> Unit,
    initialSelectedDateMillis: Long? = null,
) {

//    val datePickerState =
//        rememberDatePickerState(
//            initialSelectedDateMillis = initialSelectedDateMillis,
//            selectableDates = object : SelectableDates {
//                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
//                    return utcTimeMillis >= System.currentTimeMillis()
//                }
//            }
//        )
//
//    DatePickerDialog(
//        onDismissRequest = onDismiss,
//        confirmButton = {
//            TextButton(onClick = {
//                onDateSelected(
//                    datePickerState.selectedDateMillis?.formattedDateFromMillis().orEmpty()
//                )
//                onDismiss()
//            }) {
//                Text(stringResource(R.string.ok), style = CompactTypography.labelMedium)
//            }
//        },
//        dismissButton = {
//            TextButton(onClick = onDismiss) {
//                Text(stringResource(R.string.cancel), style = CompactTypography.labelMedium)
//            }
//        }
//    ) {
//        DatePicker(state = datePickerState)
//    }
}
