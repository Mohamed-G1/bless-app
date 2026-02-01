package com.nat.bless.screens.routeDetails.presentation.compoenets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nat.bless.base.ui.appButton.AppButton
import com.nat.bless.screens.routeDetails.domain.models.ConfirmedAndCancelledReasonsData
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.ui.theme.MediumBlue
import com.nat.bless.ui.theme.WhiteGray

@Composable
fun RefusalReasonsBottomSheet(
    refusalList: List<ConfirmedAndCancelledReasonsData>,
    onClick: (String, Int) -> Unit,
    alreadySelectedRefusalId: Int,
) {

    var selectedRefusal by remember {
        mutableStateOf("")
    }
    var selectedRefusalId by remember {
        mutableIntStateOf(alreadySelectedRefusalId)
    }

    LazyColumn(
        modifier = Modifier
            .wrapContentSize()
            .padding(16.dp)
    ) {
        items(items = refusalList) { item ->
            RefusalItem(
                model = item,
                onClick = { name, id ->
                    selectedRefusal = name
                    selectedRefusalId = id
                },
                alreadySelectedRefusalId = selectedRefusalId
            )
        }

        item {
//            Spacer(modifier = Modifier.height(16.dp))
//            AppTextField(
//                modifier = Modifier.fillMaxWidth().height(180.dp),
//                placeholder = "إذكر أسباب إضافية",
//                label = "إذكر أسباب إضافية",
//                textUnFocusColor = Color.Gray,
//                value = comments,
//                onValueChange = {
//                    comments = it
//                },
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Text,
//                )
//            )

            Spacer(modifier = Modifier.height(16.dp))

            AppButton(
                text = "تأكيد",
                modifier = Modifier.fillMaxWidth(),
                onClick = { onClick.invoke(selectedRefusal, selectedRefusalId) }
            )
        }

    }
}

@Composable
private fun RefusalItem(
    model: ConfirmedAndCancelledReasonsData,
    onClick: (String, Int) -> Unit,
    alreadySelectedRefusalId: Int
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = model.name,
                style = CompactTypography.labelMedium.copy(color = Color.Gray)
            )

            RadioButton(
                selected = alreadySelectedRefusalId == model.id,
                onClick = {
                    onClick.invoke(model.name, model.id)
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