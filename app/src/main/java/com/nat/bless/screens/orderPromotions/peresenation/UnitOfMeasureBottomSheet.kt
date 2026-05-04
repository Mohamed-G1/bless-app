package com.nat.bless.screens.orderPromotions.peresenation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.bless.base.ui.appButton.AppButton
import com.nat.bless.screens.orderPromotions.domain.models.AllowedProduct
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.ui.theme.Gray
import com.nat.bless.ui.theme.MediumBlue
import com.nat.bless.ui.theme.NotDeliverRed
import com.nat.bless.ui.theme.WhiteGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitOfMeasureBottomSheet(
    sheetState: SheetState,
    item: AllowedProduct,
    currentQty: Int,
    onDismiss: () -> Unit,
    onConfirm: (qty: Int) -> Unit,
    onRemove: (productId: Int) -> Unit,
) {
    // Pre-fill with current qty if item already selected, otherwise empty
    var qtyInput by remember(item.id) {
        mutableStateOf(if (currentQty > 0) currentQty.toString() else "")
    }
    var isError by remember { mutableStateOf(false) }

    val isAlreadyAdded = currentQty > 0
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = White,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 4.dp)
                    .width(40.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(WhiteGray)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "تحديد الكمية",
                    style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
                )
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Gray
                    )
                }
            }

            HorizontalDivider(color = WhiteGray)

            // Product info
            Text(
                text = item.name,
                style = CompactTypography.headlineLarge.copy(fontSize = 14.sp)
            )
            Text(
                text = "السعر: ${item.price}",
                style = CompactTypography.headlineLarge.copy(
                    fontSize = 13.sp,
                    color = Gray
                )
            )
            // Quantity input
            OutlinedTextField(
                value = qtyInput,
                onValueChange = { value ->
                    // Only allow numeric input
                    if (value.all { it.isDigit() }) {
                        qtyInput = value
                        isError = false
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                label = {
                    Text(
                        "الكمية",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                },
                placeholder = {
                    Text(
                        "أدخل الكمية",
                        style = CompactTypography.headlineLarge.copy(
                            fontSize = 12.sp,
                            color = Gray.copy(alpha = 0.5f)
                        )
                    )
                },
                isError = isError,
                supportingText = if (isError) {
                    {
                        Text(
                            "الرجاء إدخال كمية صحيحة",
                            style = CompactTypography.headlineLarge.copy(
                                fontSize = 11.sp,
                                color = NotDeliverRed
                            )
                        )
                    }
                } else null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        val qty = qtyInput.toIntOrNull()
                        if (qty != null && qty > 0) {
                            onConfirm(qty)
                        } else {
                            isError = true
                        }
                    }
                ),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MediumBlue,
                    unfocusedBorderColor = WhiteGray,
                    errorBorderColor = NotDeliverRed,
                    focusedLabelColor = MediumBlue,
                )
            )

            // Confirm button
            AppButton(
                modifier = Modifier.fillMaxWidth(),
                text = if (isAlreadyAdded) "تحديث الكمية" else "إضافة",
                onClick = {
                    val qty = qtyInput.toIntOrNull()
                    if (qty != null && qty > 0) {
                        onConfirm(qty)
                    } else {
                        isError = true
                    }
                }
            )

            // Remove button – only shown if product is already in the list
            if (isAlreadyAdded) {
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onRemove(item.id) },
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, NotDeliverRed),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = NotDeliverRed
                    )
                ) {
                    Text(
                        "إزالة من القائمة",
                        style = CompactTypography.headlineLarge.copy(fontSize = 14.sp)
                    )
                }
            }
        }
    }
}