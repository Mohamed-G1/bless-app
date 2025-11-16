package com.nat.greco.base.ui.datePicker

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import com.nat.greco.ui.theme.CompactTypography
import com.nat.greco.utils.formattedDateFromMillis

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePicker(
    onDateSelected: (String?) -> Unit,
    onDismiss: () -> Unit,
    initialSelectedDateMillis: Long? = null,
) {


    val currentContext = androidx.compose.ui.platform.LocalContext.current
    val arabicLocale = java.util.Locale("ar")
    java.util.Locale.setDefault(arabicLocale)

    val arabicConfig = android.content.res.Configuration(currentContext.resources.configuration).apply {
        setLocales(android.os.LocaleList(arabicLocale))
    }
    val arabicContext = currentContext.createConfigurationContext(arabicConfig)

    val datePickerState =
        rememberDatePickerState(
            initialSelectedDateMillis = initialSelectedDateMillis,
//            selectableDates = object : SelectableDates {
//                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
//                    return utcTimeMillis >= System.currentTimeMillis()
//                }
//            }
        )

    androidx.compose.runtime.CompositionLocalProvider(
        androidx.compose.ui.platform.LocalContext provides arabicContext,
        androidx.compose.ui.platform.LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Rtl
    ) {
        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    onDateSelected(
                        datePickerState.selectedDateMillis?.formattedDateFromMillis().orEmpty()
                    )
                    onDismiss()
                }) {
                    Text("اختر", style = CompactTypography.labelMedium)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("الغاء", style = CompactTypography.labelMedium)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
