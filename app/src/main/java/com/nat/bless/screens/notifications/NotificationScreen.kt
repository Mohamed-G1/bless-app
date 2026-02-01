package com.nat.bless.screens.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.bless.R
import com.nat.bless.ui.theme.CompactTypography

@Composable
fun NotificationScreen(
    onBackClicked: (() -> Unit)? = null

) {

   Column (
        modifier = Modifier
            .padding(vertical = 24.dp, horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {

       Row(
           modifier = Modifier.fillMaxWidth(),
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.SpaceBetween
       ) {
           Text(
               "التنبيهات",
               style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
           )

           IconButton(onClick = { onBackClicked?.invoke() }) {
               Icon(
                   painter = painterResource(R.drawable.ic_back),
                   contentDescription = null
               )
           }
       }



       Spacer(modifier = Modifier.height(180.dp))

       Image(painter = painterResource(R.drawable.ic_no_notification), contentDescription = null,
           )

       Spacer(modifier = Modifier.height(24.dp))

       Text(
           "لا يوجد لديك تنبيهات",
           style = CompactTypography.headlineMedium.copy(fontSize = 18.sp, textAlign = TextAlign.Center),
           modifier = Modifier.fillMaxWidth()
       )


   }

}

@Preview
@Composable
private fun NotificationScreenPreview() {
    NotificationScreen()
}