package com.nat.couriersapp.base.permissions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.nat.couriersapp.ui.theme.CompactTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
) {

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier,
        properties = DialogProperties()

    ) {
        Card(
            modifier = Modifier
                .wrapContentSize(),
            colors = CardDefaults.cardColors(containerColor = Color.White)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "مطلوب الإذن",
                    style = CompactTypography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = permissionTextProvider.getDescription(
                        isPermanentlyDeclined = isPermanentlyDeclined
                    ),
                    style = CompactTypography.titleMedium.copy(lineHeight = 22.sp),
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HorizontalDivider()
                    Text(
                        text = if (isPermanentlyDeclined) {
                            "منح الإذن"
                        } else {
                            "نعم"
                        },
                        textAlign = TextAlign.Center,
                        style = CompactTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (isPermanentlyDeclined) {
                                    onGoToAppSettingsClick()
                                } else {
                                    onOkClick()
                                }
                            }
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}

class CameraPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "يبدو أنك رفضت إذن الكاميرا بشكل دائم. " +
                    "يمكنك الانتقال إلى إعدادات التطبيق لمنحه."
        } else {
            "يحتاج هذا التطبيق إلى الوصول إلى الكاميرا الخاصة بك حتى تتمكن من التقاط الصور للشحنات"
        }
    }
}

class LocationPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "يبدو أنك رفضت إذن تحديد الموقع بشكل دائم. " +
                    "لتتمكن من استخدام التطبيق يمكنك الانتقال إلى الاعدادات لمنحه"
        } else {
            "يحتاج هذا التطبيق إلى الوصول إلى موقعك حتى تتمكن من استخدام التطبيق"
        }
    }
}

class NotificationsPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "يبدو أنك رفضت إذن الإشعارات بشكل دائم. " +
                    "يمكنك الانتقال إلى إعدادات التطبيق لمنحه"
        } else {
            "يحتاج هذا التطبيق إلى الوصول إلى أذونات الإشعارات حتى يتم تسليم الإشعارات إليك"
        }
    }
}