package com.nat.bless.application.base.presentation.otp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OtpMultiDigits(
    modifier: Modifier = Modifier,
    code: String = "",
    fieldsNumber: Int = 6,
    boxSize: Dp = 45.dp,
    borderFocusColor: Color = MaterialTheme.colorScheme.primary,
    borderUnFocusColor: Color = Color.LightGray,
    textFocusColor: Color = MaterialTheme.colorScheme.primary,
    textUnFocusColor: Color = Color.Black,
    separatorColor: Color = MaterialTheme.colorScheme.primary,
    onValueChange: ((String) -> Unit)? = null,
    onCodeComplete: ((String) -> Unit)? = null,
) {

    val shouldAddSeparator = fieldsNumber % 2 == 0

    BasicTextField(
        modifier = modifier,
        value = code,
        onValueChange = {
            if (it.length <= fieldsNumber) {
                onValueChange?.invoke(it)
                if (it.length == fieldsNumber) {
                    onCodeComplete?.invoke(it)
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        ),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(fieldsNumber) { index ->
                    OtpSingleDigit(
                        text = getSingleCharacterCode(code, index),
                        hasFocus = index == code.lastIndex,
                        boxSize = boxSize,
                        textFocusColor = textFocusColor,
                        textUnFocusColor = textUnFocusColor,
                        borderFocusColor = borderFocusColor,
                        borderUnFocusColor = borderUnFocusColor
                    )

                    if (shouldAddSeparator && index == (fieldsNumber / 2 - 1)) {
                        Text(
                            text = "",
                            color = separatorColor,
                            fontSize = 24.sp
                        )
                    }
                }
            }
        },
    )
}

fun getSingleCharacterCode(code: String, index: Int): String {
    return when {
        index == code.length -> ""
        index > code.length -> ""
        else -> code[index].toString()
    }
}


@Preview
@Composable
private fun OtpMultiViewPreview() {
    OtpMultiDigits(code = "125")
}


/// reference
// https://proandroiddev.com/jetpack-compose-create-my-own-textfield-c1b4f7e4d27f

