package com.nat.greco.base.ui.toast

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private object ToastHandler {
    var toast: Toast? = null
}

private fun createToast(
    message: String,
    duration: Int,
    context: Context
) = Toast.makeText(context, message, duration)

private fun hideLastToast(toast: Toast? = ToastHandler.toast) {
    toast?.cancel()
}

private fun showNewToast(toast: Toast? = ToastHandler.toast) {
    toast?.show()
}

@Composable
fun ShowToast(
    message: String,
    duration: Int = Toast.LENGTH_LONG,
    context: Context = LocalContext.current
) {
    hideLastToast()
    ToastHandler.toast = createToast(message, duration, context)
    showNewToast()
}

fun showToast(
    message: String,
    duration: Int = Toast.LENGTH_LONG,
    context: Context
) {
    hideLastToast()
    ToastHandler.toast = createToast(message, duration, context)
    showNewToast()
}