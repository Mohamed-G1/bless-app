package com.nat.couriersapp.screens.courierDetails.presentation.compoenets

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nat.couriersapp.base.ui.appButton.AppButton
import com.nat.couriersapp.screens.courierDetails.domain.models.StatusNotDeliveredModel
import com.nat.couriersapp.ui.theme.CompactTypography
import com.nat.couriersapp.ui.theme.MediumBlue
import com.nat.couriersapp.ui.theme.WhiteGray

@Composable
fun NotDeliveredBottomSheet(
    statusList: List<StatusNotDeliveredModel>,
    onClick: (String, Int) -> Unit,
) {

    var selectedStatus by remember {
        mutableStateOf("")
    }
    var selectedStatusId by remember {
        mutableIntStateOf(0)
    }


    LazyColumn(
        modifier = Modifier
            .wrapContentSize()
            .padding(16.dp)
    ) {
        items(items = statusList) { item ->
            StatusItem(
                model = item,
                onClick = { name, id ->
                    selectedStatus = name
                    selectedStatusId = id
                },
                alreadySelectedStatusId = selectedStatusId
            )
        }

        item {
            Spacer(modifier = Modifier.height(98.dp))

            AppButton(
                text = "تأكيد",
                modifier = Modifier.fillMaxWidth(),
                onClick = { onClick.invoke(selectedStatus, selectedStatusId) }
            )
        }

    }
}


@Composable
fun StatusItem(
    model: StatusNotDeliveredModel,
    onClick: (String, Int) -> Unit,
    alreadySelectedStatusId: Int
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = model.statusNameAr.orEmpty(),
                style = CompactTypography.labelMedium.copy(color = Color.Gray)
            )

            RadioButton(
                selected = alreadySelectedStatusId == model.statusId,
                onClick = {
                    onClick.invoke(model.statusNameAr.orEmpty(), model.statusId ?: 0)
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
//    NotDeliveredBottomSheet()
}