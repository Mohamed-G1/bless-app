package com.nat.bless.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.bless.R
import com.nat.bless.base.ui.appLoading.FullLoading
import com.nat.bless.base.ui.dialog.AppDialog
import com.nat.bless.base.ui.toast.ShowToast
import com.nat.bless.ui.theme.CompactTypography

@Composable
fun ProfileScreen(
    state: ProfileState = ProfileState(),
    events: ((ProfileEvents) -> Unit)? = null,

    navigateToDeliveryReport: (() -> Unit)? = null,
    navigateToDeliveryTarget: (() -> Unit)? = null,
    navigateToPayslip: (() -> Unit)? = null,
    signOut: (() -> Unit)? = null


) {
    var showSignoutDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp).safeContentPadding()
            .verticalScroll(
                rememberScrollState()
            ),

        ) {

        Text(
            stringResource(R.string.profile),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
        )
        Spacer(Modifier.height(24.dp))

        ProfileItem(
            title = "تارجت المندوب",
            onClick = {

                navigateToDeliveryTarget?.invoke()
            }
        )

        Spacer(Modifier.height(16.dp))
        ProfileItem(
            title = "تقرير المندوب",
            onClick = {

                navigateToDeliveryReport?.invoke()
            }
        )

        Spacer(Modifier.height(16.dp))
        ProfileItem(
            title = "تفاصيل المرتب",
            onClick = {
                navigateToPayslip?.invoke()
            }
        )
        Spacer(Modifier.height(16.dp))

        ProfileItem(
            title = "خروج",
            onClick = {
                showSignoutDialog = true
            }
        )
    }


    if (showSignoutDialog) {
        AppDialog(
            dialogMessage = "هل أنت متأكد من تسجيل الخروج؟",
            onDismiss = { showSignoutDialog = false },
            onConfirm = {
                showSignoutDialog = false
                events?.invoke(ProfileEvents.ClearUser)
                signOut?.invoke()
            },
            confirmButtonTitle = "نعم",
            cancelButtonTitle = "لا"
        )
    }
    if (state.error.isNotEmpty()) {
        ShowToast(state.error)
    }

    if (state.isLoading) {
        FullLoading()
    }
}
@Composable
private fun ProfileItem(
    title: String,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick.invoke() },
        color = White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(8.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style =CompactTypography.headlineMedium.copy(fontSize = 14.sp)
            )

            Spacer(modifier = Modifier.weight(1f))


            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null,
            )
        }
    }

}


@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen()
}