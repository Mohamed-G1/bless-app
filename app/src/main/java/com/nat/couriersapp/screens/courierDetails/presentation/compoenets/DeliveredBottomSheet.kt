package com.nat.couriersapp.screens.courierDetails.presentation.compoenets

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nat.couriersapp.R
import com.nat.couriersapp.base.ui.appButton.AppButton
import com.nat.couriersapp.base.ui.textField.AppTextField
import com.nat.couriersapp.screens.courierDetails.presentation.CourierDetailsEvents
import com.nat.couriersapp.screens.courierDetails.presentation.CourierDetailsState
import com.nat.couriersapp.ui.theme.CompactTypography
import com.nat.couriersapp.ui.theme.LightGray
import com.nat.couriersapp.ui.theme.MediumBlue
import com.nat.couriersapp.ui.theme.WhiteGray
import com.nat.couriersapp.utils.createSignatureBitmap

@Composable
fun DeliveredBottomSheet(
    state: CourierDetailsState,
    events: ((CourierDetailsEvents) -> Unit)? = null,
    onDismiss: (() -> Unit)? = null
) {

    var showSignature by remember {
        mutableStateOf(false)
    }

    if (showSignature.not()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            Text("توقيع العميل", style = CompactTypography.headlineMedium)

            Spacer(Modifier.height(24.dp))

            ClientNameTextField(state, events)

            Spacer(Modifier.height(24.dp))

            AppButton(
                text = "تأكيد",
                buttonColor = MediumBlue,
                onClick = {
                    showSignature = true
                }
            )
        }


    } else {
        ClientSignatureArea(events = events, onDismiss = onDismiss)
    }
}

@Composable
private fun ClientNameTextField(
    state: CourierDetailsState,
    event: ((CourierDetailsEvents) -> Unit)?,
) {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = stringResource(R.string.client_name),
        label = stringResource(R.string.client_name),
        isError = !state.isValidClientName,
        errorMessage = if (state.isValidClientName) null else stringResource(id = state.clientNameValidationMessage),
        value = state.clientName,
        onValueChange = {
            event?.invoke(CourierDetailsEvents.ClientNameChanged(it))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        )
    )
}

@Composable
fun ClientSignatureArea(
    events: ((CourierDetailsEvents) -> Unit)? = null,
    onDismiss: (() -> Unit)? = null
) {
    // State to hold the drawing path
    val path by remember { mutableStateOf(Path()) }
    var currentpath by remember { mutableStateOf(path) }
    var saveBitmap by remember { mutableStateOf(false) }
    var isSigned by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }
    val density = LocalDensity.current
    val canvasHeight = 300.dp
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    var cardBorderColor by remember { mutableStateOf(WhiteGray) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text("توقيع العميل", style = CompactTypography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            colors = CardDefaults.cardColors(containerColor = LightGray),
            border = BorderStroke(1.dp, color = cardBorderColor)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(canvasHeight)
                    .clipToBounds()
                    .pointerInput(true) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                path.moveTo(offset.x, offset.y)
                                isSigned = true
                                currentPosition = offset
                            },
                            onDrag = { change, _ ->
                                path.lineTo(change.position.x, change.position.y)
                                currentPosition = change.position
                                isSigned = true
                            }
                        )
                    }
            ) {
                if (currentPosition != Offset.Unspecified) {
                    drawPath(
                        path = path,
                        color = Color.Black,
                        style = Stroke(
                            width = 3.dp.toPx(),
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )

                }

                if (saveBitmap) {
                    // create and save bitmap in remeber variable
                    events?.invoke(
                        CourierDetailsEvents.ClientSignatureChanged(

                            bimap = createSignatureBitmap(
                                path,
                                size.width,
                                with(density) { canvasHeight.toPx() }.toInt()
                            )
                        )
                    )
                    saveBitmap = false
                }
            }
        }


        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            AppButton(
                text = "تأكيد",
                buttonColor = MediumBlue,
                modifier = Modifier.weight(.8f),
                onClick = {
                    if (isSigned){
                        saveBitmap = true
                    }
                    onDismiss?.invoke()
                })

            Spacer(modifier = Modifier.width(12.dp))

            AppButton(
                text = "مسح",
                buttonColor = Color.White,
                boarderColor = MediumBlue,
                textColor = MediumBlue,
                modifier = Modifier.weight(.3f),
                onClick = {
                    path.reset() // Clear all paths
                    currentpath = Path() // Reset the current path
                    currentPosition = Offset.Unspecified // Reset the position
                    isSigned = false //urrent path
                })

        }

    }


}

@Preview
@Composable
private fun DeliverdBottomSheetPreview() {
    DeliveredBottomSheet(CourierDetailsState())
}

@Preview
@Composable
private fun ClientSignatureAreaPreview() {
    ClientSignatureArea()
}
