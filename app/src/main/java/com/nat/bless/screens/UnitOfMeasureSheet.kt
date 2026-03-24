package com.nat.bless.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.bless.base.ui.appButton.AppButton
import com.nat.bless.screens.addNewOrders.models.StockListData
import com.nat.bless.screens.addNewOrders.models.SelectedUnit
import com.nat.bless.ui.theme.CompactTypography

@Composable
fun UnitOfMeasureSheet(
    data: StockListData? = null,
    onAddClicked: ((List<SelectedUnit>) -> Unit)? = null,
    selectedUnits: SnapshotStateList<SelectedUnit>,
) {

//    val selectedUnits = remember { mutableStateListOf<SelectedUnit>() }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        data?.uom_prices?.forEach { uom ->

            val existingIndex = selectedUnits.indexOfFirst {
                it.productId == data.product_id && it.uomId == uom.uom_id
            }

            val currentQuantity =
                if (existingIndex != -1) selectedUnits[existingIndex].quantity else 0

            // ✅ local text state per uom row
            var text by remember(data.id, uom.uom_id) {
                mutableStateOf(if (currentQuantity == 0) "" else currentQuantity.toString())
            }

            // ✅ keep text in sync if list changes from outside (optional but good)
            LaunchedEffect(currentQuantity) {
                val newText = if (currentQuantity == 0) "" else currentQuantity.toString()
                if (text != newText) text = newText
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(uom.uom_name, maxLines = 1)
                    Text("${uom.price} EGP", maxLines = 1)
                }

                OutlinedTextField(
                    value = text,
                    onValueChange = { newText ->
                        // ✅ allow typing freely, but keep only digits
                        val filtered = newText.filter { it.isDigit() }
                        text = filtered

                        val newQuantity = filtered.toIntOrNull() ?: 0

                        when {
                            newQuantity > 0 && existingIndex == -1 -> {
                                selectedUnits.add(
                                    SelectedUnit(
                                        id = data.id,
                                        productId = data.product_id,
                                        uomId = uom.uom_id,
                                        uomName = uom.uom_name,
                                        price = uom.price,
                                        quantity = newQuantity,
                                        lot_id = data.lot_id,
                                        lot_name = data.lot_name
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
                    modifier = Modifier.width(120.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text("الكمية") }
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