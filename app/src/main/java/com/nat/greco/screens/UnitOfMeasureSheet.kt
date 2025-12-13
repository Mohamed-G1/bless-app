package com.nat.greco.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.greco.base.ui.appButton.AppButton
import com.nat.greco.screens.addNewOrders.models.StockListData
import com.nat.greco.screens.addNewOrders.models.SelectedUnit
import com.nat.greco.screens.addNewOrders.presentation.Counter
import com.nat.greco.ui.theme.CompactTypography

@Composable
fun UnitOfMeasureSheet(
    data: StockListData? = null,
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
//                Text(
//                    "${uom.uom_name} السعر: ${uom.price}",
//                    style = CompactTypography.headlineMedium.copy(fontSize = 14.sp)
//                            ,modifier = Modifier
//                            .weight(1f) // 👈 gives text flexible width
//                        .padding(end = 8.dp),
//                    maxLines = 2, // 👈 optional: wrap text but keep height consistent
//                    overflow = TextOverflow.Ellipsis
//                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                    ,modifier = Modifier
                            .weight(1f) // 👈 gives text flexible width
                        .padding(end = 8.dp),
                ) {
                    Text(
                        uom.uom_name,
                        style = CompactTypography.headlineMedium.copy(fontSize = 14.sp),
                        maxLines = 1, // 👈 optional: wrap text but keep height consistent
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        uom.price.toString() + " EGP",
                        style = CompactTypography.headlineMedium.copy(fontSize = 14.sp),
                        maxLines = 1, // 👈 optional: wrap text but keep height consistent
                        overflow = TextOverflow.Ellipsis
                    )

                }

                OutlinedTextField(
                    value = if (currentQuantity == 0) "" else currentQuantity.toString(),
                    singleLine = true,
                    onValueChange = { text ->
                        val newQuantity = text.toIntOrNull() ?: 0

                        val existingIndex = selectedUnits.indexOfFirst {
                            it.productId == data.id && it.uomId == uom.uom_id
                        }

                        when {
                            newQuantity > 0 && existingIndex == -1 -> {
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
                                selectedUnits[existingIndex] =
                                    selectedUnits[existingIndex].copy(quantity = newQuantity)
                            }

                            newQuantity == 0 && existingIndex != -1 -> {
                                selectedUnits.removeAt(existingIndex)
                            }
                        }
                    },
                    modifier = Modifier
                        .width(120.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    label = { Text("الكمية", style = CompactTypography.headlineMedium.copy(fontSize = 14.sp)) }
                )


            }
        }

        Spacer(Modifier.height(32.dp))
        val context = LocalContext.current

        AppButton(
            text = "اضافة",
            onClick = {
                val totalSelectedQuantity = selectedUnits
                    .filter { it.productId == data?.id }
                    .sumOf { it.quantity }

                val maxQuantity = data?.quantity ?: 0.0

                if (totalSelectedQuantity > maxQuantity) {
                    Toast.makeText(
                        context,
                        "الكمية المختارة أكبر من الكمية المتاحة (${maxQuantity})",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    onAddClicked?.invoke(selectedUnits)
                }
            }
        )


    }

}