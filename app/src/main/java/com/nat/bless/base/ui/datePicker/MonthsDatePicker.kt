package com.nat.bless.base.ui.datePicker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nat.bless.R
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.utils.formattedDateFromMillis
import java.time.Month

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthYearPickerDialog(
    onConfirm: (year: Int, month: Int) -> Unit,
    onDismiss: () -> Unit,
    initialYear: Int = java.time.YearMonth.now().year,
    initialMonth: Int = java.time.YearMonth.now().monthValue, // 1..12
    yearRange: IntRange = (2000..2100),
    forceArabic: Boolean = true
) {
    val context = LocalContext.current

    // Use Arabic locale if requested, otherwise device locale
    val locale = remember(forceArabic) {
        if (forceArabic) java.util.Locale("ar") else context.resources.configuration.locales[0]
    }

    // Key ensures state initializes once per "open" with these initials
    val initKey = remember(initialYear, initialMonth) { "$initialYear-$initialMonth" }

    var selectedYear by remember(initKey) { mutableIntStateOf(initialYear) }
    var selectedMonth by remember(initKey) { mutableIntStateOf(initialMonth) } // 1..12

    val months = remember { (1..12).toList() }
    val years = remember(yearRange) { yearRange.toList() }

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { onConfirm(selectedYear, selectedMonth) }) {
                Text("موافق")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("إلغاء")
            }
        }
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MonthDropdownArabic(
                selectedMonth = selectedMonth,
                onMonthSelected = { selectedMonth = it },
                months = months,
                locale = locale
            )

            YearDropdown(
                selectedYear = selectedYear,
                onYearSelected = { selectedYear = it },
                years = years
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MonthDropdownArabic(
    selectedMonth: Int,                 // 1..12
    onMonthSelected: (Int) -> Unit,
    months: List<Int>,                  // 1..12
    locale: java.util.Locale
) {
    var expanded by remember { mutableStateOf(false) }

    // Display label for selected month
    val selectedLabel = remember(selectedMonth, locale) {
        Month.of(selectedMonth)
            .getDisplayName(java.time.format.TextStyle.FULL, locale)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            value = selectedLabel,
            onValueChange = {},
            label = { Text("الشهر") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            months.forEach { monthValue ->
                val label = java.time.Month.of(monthValue)
                    .getDisplayName(java.time.format.TextStyle.FULL, locale)

                DropdownMenuItem(
                    text = { Text(label) },
                    onClick = {
                        onMonthSelected(monthValue) // ✅ exact selected month (1..12)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun YearDropdown(
    selectedYear: Int,
    onYearSelected: (Int) -> Unit,
    years: List<Int>
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            value = selectedYear.toString(),
            onValueChange = {},
            label = { Text("السنة") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            years.forEach { y ->
                DropdownMenuItem(
                    text = { Text(y.toString()) },
                    onClick = {
                        onYearSelected(y)
                        expanded = false
                    }
                )
            }
        }
    }
}