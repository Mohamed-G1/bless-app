package com.nat.bless.base.ui.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.ui.theme.MediumBlue
import com.nat.bless.ui.theme.NotDeliverRed
import com.nat.bless.ui.theme.WhiteGray

@Composable
fun AppDialog(
    onConfirm: ((isConfirmed: Boolean) -> Unit)? = null,
    onDismiss: ((isDismiss: Boolean) -> Unit)? = null,
     dialogMessage: String = "",

    dialogStringMessage: String = "",
    confirmButtonTitle: String = "",
    cancelButtonTitle: String = "",
//    icon: Painter = painterResource(id = R.drawable.flash_yellow),
    showActionButtons: Boolean = true,
) {

    Dialog(onDismissRequest = { onDismiss?.invoke(false) }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(Color.White),
        ) {

            IconButton(onClick = { onDismiss?.invoke(true) }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.LightGray
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)
                    .background(color = Color.Transparent),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                Image(painter = icon, contentDescription = null, modifier = Modifier.size(120.dp))
                Text(
                    text = dialogStringMessage.ifBlank {
                        dialogMessage
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    style = CompactTypography.bodyMedium.copy(textAlign = TextAlign.Center)
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (showActionButtons) {
                    Row(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { onConfirm?.invoke(true) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MediumBlue
                            )
                        ) {
                            Text(
                                text = confirmButtonTitle,
                                modifier = Modifier.padding(horizontal = 12.dp),
                                style = CompactTypography.bodyMedium.copy(color = Color.White)
                            )
                        }

                        Button(
                            onClick = { onDismiss?.invoke(true) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NotDeliverRed
                            ),
                            border = BorderStroke(1.dp, WhiteGray)
                        ) {
                            Text(
                                text = cancelButtonTitle,
                                modifier = Modifier.padding(horizontal = 12.dp),
                                style = CompactTypography.bodyMedium.copy(color = Color.White)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(locale = "ar")
@Composable
fun AppDialogP1AR() {
    AppDialog(
        onConfirm = {},
        onDismiss = {},
        showActionButtons = false

    )
}

@Preview
@Composable
fun AppDialogP1() {
    AppDialog(
        onConfirm = {},
        onDismiss = {},

        )
}