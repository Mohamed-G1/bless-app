package com.nat.couriersapp.base.ui.appLoading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun FullLoading() {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        ),
    ) {
        Surface (
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = Color.White,
                    shape = MaterialTheme.shapes.large
                )
                .padding(20.dp),
            color = Color.White
        ) {
            BaseLoading()
        }
    }
}

@Composable
fun SmallLoading() {
    BaseLoading()
}

@Composable
private fun BaseLoading() {
    CircularProgressIndicator(
        color = MaterialTheme.colorScheme.primary
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FullLoadingPreview() {
    FullLoading()
}

@Preview
@Composable
fun SmallLoadingPreview() {
    SmallLoading()
}