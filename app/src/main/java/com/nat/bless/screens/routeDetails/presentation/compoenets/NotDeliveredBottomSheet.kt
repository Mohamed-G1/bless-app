package com.nat.bless.screens.routeDetails.presentation.compoenets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.nat.bless.base.ui.button.AppButton
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.ui.theme.MediumBlue
import com.nat.bless.ui.theme.WhiteGray

@Composable
fun NotDeliveredBottomSheet(    onClick: (String) -> Unit,
) {
    val reasons = listOf(
        "عميل غير موجود",
        "تعثر الوصول للعميل",
        "هاتف العميل خطأ"
    )
    var selectedFilter by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(16.dp)
    ) {
        reasons.forEach { filter ->
            NotDeliveredItem(
                item = filter,
                onClick = {
//                    selectedFilter = filter
                },
                alreadySelectedFilter = filter
            )
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        AppButton(
            text = "تأكيد",
            modifier = Modifier.weight(.9f),
            onClick = {
                onClick.invoke(selectedFilter)
            }
        )
//        Spacer(modifier = Modifier.width(12.dp))
//        AppButton(
//            text = "إعادة ضبط",
//            modifier = Modifier.weight(.5f),
//            buttonColor = Color.White,
//            boarderColor = MediumBlue,
//            textColor = MediumBlue,
//            onClick = {
//                onResetClick.invoke()
//            }
//        )
    }
}

@Composable
private fun NotDeliveredItem(
    item: String,
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
                text = item,
                style = CompactTypography.labelMedium.copy(color = Color.Gray)
            )

            RadioButton(
                selected = false,
                onClick = {
                    onClick.invoke(item)
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
private fun NotDeliveredBottomSheetPreview() {
    NotDeliveredBottomSheet({})
}