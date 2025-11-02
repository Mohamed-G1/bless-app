package com.nat.greco.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.greco.base.ui.appButton.AppButton
import com.nat.greco.screens.addNewOrders.models.Data
import com.nat.greco.screens.addNewOrders.models.SelectedUnit
import com.nat.greco.screens.addNewOrders.presentation.Counter
import com.nat.greco.ui.theme.CompactTypography

@Composable
fun UnitOfMeasureSheet(
    data: Data? = null,
    onAddClicked: ((List<SelectedUnit>) -> Unit)? = null,
    selectedUnits: SnapshotStateList<SelectedUnit>,
) {

//    val selectedUnits = remember { mutableStateListOf<SelectedUnit>() }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        data?.uom_prices?.forEach { uom ->
            val currentQuantity = selectedUnits.find {
                it.productId == data.id && it.uomId == uom.uom_id
            }?.quantity ?: 0


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${uom.uom_name} السعر: ${uom.price}",
                    style = CompactTypography.headlineMedium.copy(fontSize = 14.sp)
                            ,modifier = Modifier
                            .weight(1f) // 👈 gives text flexible width
                        .padding(end = 8.dp),
                    maxLines = 2, // 👈 optional: wrap text but keep height consistent
                    overflow = TextOverflow.Ellipsis
                )

                Counter(
                    value = currentQuantity,
                    onCounterChange = { newQuantity ->
                        val existingIndex = selectedUnits.indexOfFirst {
                            it.productId == data.id && it.uomId == uom.uom_id
                        }

                        when {
                            newQuantity > 0 && existingIndex == -1 -> {
                                // Add new selection
                                selectedUnits.add(
                                    SelectedUnit(
                                        productId = data.id,
                                        uomId = uom.uom_id,
                                        uomName = uom.uom_name,
                                        price = uom.price,
                                        quantity = newQuantity
                                    )
                                )
                            }

                            newQuantity > 0 && existingIndex != -1 -> {
                                // Update quantity
                                selectedUnits[existingIndex] =
                                    selectedUnits[existingIndex].copy(quantity = newQuantity)
                            }

                            newQuantity == 0 && existingIndex != -1 -> {
                                // Remove if quantity reset to 0
                                selectedUnits.removeAt(existingIndex)
                            }
                        }
                    },
                    modifier = Modifier
                        .width(130.dp) // 👈 fixed width for consistent alignment
                        .height(50.dp),
                )
            }
        }

//        Spacer(Modifier.height(8.dp))
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                "الوحدة (كرتونة)",
//                style = CompactTypography.headlineMedium.copy(fontSize = 14.sp)
//            )
//            Spacer(modifier = Modifier.weight(1f))
//
//            Counter(
//                onCounterChange = {},
//                modifier = Modifier.fillMaxWidth(.6f)
//                    .height(40.dp),
//            )
//        }


        Spacer(Modifier.height(32.dp))
        AppButton(
            text = "اضافة",
            onClick = {
                onAddClicked?.invoke(selectedUnits)
            }
        )

    }

}