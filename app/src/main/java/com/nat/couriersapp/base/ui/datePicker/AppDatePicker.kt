package com.nat.couriersapp.application.base.presentation.datePicker

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable

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
