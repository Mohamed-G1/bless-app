package com.nat.greco.screens.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nat.greco.base.ui.appButton.AppButton
import com.nat.greco.screens.home.domain.models.SortOptions
import com.nat.greco.ui.theme.CompactTypography
import com.nat.greco.ui.theme.MediumBlue
import com.nat.greco.ui.theme.WhiteGray

@Composable
fun SortBottomSheetLayout(
    onClick: (String) -> Unit,
    onResetClick: () -> Unit,
    alreadySelectedSort: String
) {

    val filtersList = SortOptions.entries

    var selectedFilter by remember {
        mutableStateOf(alreadySelectedSort)
    }

    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(16.dp)
    ) {
        filtersList.forEach { filter ->
            SortItem(
                filter = filter,
                onClick = {
                    selectedFilter = filter.name
                },
                alreadySelectedFilter = selectedFilter
            )
        }

        Spacer(modifier = Modifier.height(98.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            AppButton(
                text = "تأكيد",
                modifier = Modifier.weight(.5f),
                onClick = {
                    onClick.invoke(selectedFilter)
                }
            )
            Spacer(modifier = Modifier.width(12.dp))
            AppButton(
                text = "إعادة ضبط",
                modifier = Modifier.weight(.5f),
                buttonColor = Color.White,
                boarderColor = MediumBlue,
                textColor = MediumBlue,
                onClick = {
                    onResetClick.invoke()
                }

            )
        }
    }


}


@Composable
fun SortItem(
    filter: SortOptions,
    onClick: (String) -> Unit,
    alreadySelectedFilter: String

) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = filter.value,
                style = CompactTypography.labelMedium.copy(color = Color.Gray)
            )

            RadioButton(
                selected = alreadySelectedFilter == filter.name,
                onClick = {
                    onClick.invoke(filter.value)
                },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MediumBlue,
                    unselectedColor = MediumBlue
                )
            )

        }

        Spacer(modifier = Modifier.height(4.dp))
        HorizontalDivider(thickness = .5.dp, color = WhiteGray)
    }
}

@Preview
@Composable
private fun SortBottomSheetLayoutPreview() {
    SortBottomSheetLayout(
        {}, {},
        alreadySelectedSort = SortOptions.LowestAmount.value
    )
}