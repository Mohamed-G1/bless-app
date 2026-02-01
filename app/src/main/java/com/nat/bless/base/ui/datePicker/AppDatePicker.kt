package com.nat.bless.base.ui.datePicker

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.utils.formattedDateFromMillis

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePicker(
    onDateSelected: (String?) -> Unit,
    onDismiss: () -> Unit,
    initialSelectedDateMillis: Long? = null,
) {


    val currentContext = LocalContext.current
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

    // Ensure the picker actually selects the provided initial date when opened or when it changes
    LaunchedEffect(initialSelectedDateMillis) {
        if (initialSelectedDateMillis != null && datePickerState.selectedDateMillis != initialSelectedDateMillis) {
            datePickerState.selectedDateMillis = initialSelectedDateMillis
        }
    }

    CompositionLocalProvider(
        LocalContext provides arabicContext,
        LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Rtl
    ) {
        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    onDateSelected(
                        datePickerState.selectedDateMillis?.formattedDateFromMillis()
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
