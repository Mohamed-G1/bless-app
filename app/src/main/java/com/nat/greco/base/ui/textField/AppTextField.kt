package com.nat.greco.base.ui.textField

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nat.greco.R
import com.nat.greco.ui.theme.CompactTypography
import com.nat.greco.ui.theme.WhiteGray

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    label: String = stringResource(id = R.string.app_name),
    placeholder: String = stringResource(id = R.string.app_name),
    onValueChange: ((String) -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    isPassword: Boolean = false,
    enabled: Boolean = true,
    isTrailingIcon: Boolean = false,
    @DrawableRes trailingIconRes: Int? = null,
    iconClick: (() -> Unit)? = null,
    trailingCompose: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    maxLines: Int = 1,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    borderFocusColor: Color = MaterialTheme.colorScheme.primary,
    borderUnFocusColor: Color = WhiteGray,
    textFocusColor: Color = colorResource(id = R.color.black),
    textUnFocusColor: Color = colorResource(id = R.color.black)
) {

    var passwordVisibility: Boolean by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier.clip(RoundedCornerShape(12.dp)).clickable { onClick?.invoke() },
        isError = isError,
        value = value,
        readOnly = readOnly,
        onValueChange = {
            onValueChange?.invoke(it)
        },
        label = {
            Text(
                text = label,
                style = CompactTypography.labelMedium.copy(color = Color.Black)
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                style = CompactTypography.labelMedium.copy(color = Color.Gray)
            )
        },
        supportingText = errorText(isError, errorMessage),
        visualTransformation = getCorrectPasswordTransformation(isPassword, passwordVisibility),
        trailingIcon = trailingIcon(
            isPassword, passwordVisibility, passwordIconClick = {
                passwordVisibility = !passwordVisibility
            }, isTrailingIcon, trailingIconRes, iconClick, trailingCompose
        ),
//        leadingIcon = {
//            leadingIcon?.invoke()
//        },
        maxLines = maxLines,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedBorderColor = borderFocusColor,
            focusedTextColor = textFocusColor,
            unfocusedBorderColor = borderUnFocusColor,
            unfocusedTextColor = textUnFocusColor,
            disabledBorderColor = borderFocusColor,
            disabledTextColor = textFocusColor,
            disabledSupportingTextColor = MaterialTheme.colorScheme.error,
            errorContainerColor = MaterialTheme.colorScheme.error.copy(alpha = .2f)
        ),
        shape = RoundedCornerShape(12.dp),
        enabled = enabled
    )
}

@Composable
fun TrailingIconTextField(@DrawableRes trailingIconRes: Int, iconClick: (() -> Unit)) {
    IconButton(onClick = {
        iconClick.invoke()
    }) {
        Icon(
            painter = painterResource(id = trailingIconRes),
            contentDescription = null,
            tint = Color.Black
        )
    }
}

fun trailingIcon(
    isPassword: Boolean,
    isPasswordVisible: Boolean,
    passwordIconClick: (() -> Unit),
    isTrailingIcon: Boolean,
    trailingIconRes: Int?,
    iconClick: (() -> Unit)? = null,
    trailingCompose: @Composable (() -> Unit)? = null
): @Composable (() -> Unit)? {
    if (isPassword) {
        return {
            val resourceId =
                if (isPasswordVisible) R.drawable.ic_password_visible else R.drawable.ic_password_invisible
            TrailingIconTextField(trailingIconRes = resourceId, iconClick = {
                passwordIconClick.invoke()
            })
        }
    } else if (isTrailingIcon && trailingIconRes != null) {
        return {
            TrailingIconTextField(trailingIconRes = trailingIconRes, iconClick = {
                iconClick?.invoke()
            })
        }
    } else {
        return trailingCompose
    }
}

fun errorText(isError: Boolean, errorMessage: String?): @Composable() (() -> Unit)? {
    if (isError) {
        return {
            Text(text = errorMessage ?: "Please add error message to display it here",style = CompactTypography.labelMedium)
        }
    } else {
        return null
    }
}

private fun getCorrectPasswordTransformation(
    isPassword: Boolean, passwordVisibility: Boolean
): VisualTransformation {
    return if (isPassword) {
        if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }
}

@Preview
@Composable
fun AppTextFieldPreview() {
    AppTextField(isError = false)
}

@Preview
@Composable
fun AppTextFieldPreview2() {
    AppTextField(
        value = "this is value of text field", isError = true
    )
}

@Preview
@Composable
fun AppTextFieldPreview3() {
    AppTextField(
        value = "this is password field", isPassword = true
    )
}

@Preview
@Composable
fun AppTextFieldPreview4() {
    AppTextField(
        value = "this is read only field", readOnly = true,
        trailingCompose = {
            Row(
                modifier = Modifier.height(30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Home,
                    contentDescription = null
                )
                Text(text = "Trailing...")
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    )
}