package com.nat.greco.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.greco.R
import com.nat.greco.base.ui.appButton.AppButton
import com.nat.greco.base.ui.textField.AppTextField
import com.nat.greco.screens.home.domain.models.HomeModel
import com.nat.greco.screens.home.presentation.HomeState.Companion.dummyList
import com.nat.greco.ui.theme.CompactTypography
import com.nat.greco.ui.theme.DarkBlue
import com.nat.greco.ui.theme.Gray
import com.nat.greco.ui.theme.MediumGray
import com.nat.greco.ui.theme.WhiteGray

@Composable
fun ReceiveStockScreen(
    onBackClicked: (() -> Unit)? = null,
    onConfirmClicked: (() -> Unit)? = null,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 16.dp)
    )  {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "استلام المخزون",
                style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
            )

            IconButton(onClick = { onBackClicked?.invoke() }) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = null
                )
            }

        }

//        Spacer(Modifier.height(16.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//
//            IconButton(onClick = {
//
//            }) {
//                Icon(
//                    painter = painterResource(R.drawable.ic_scan),
//                    contentDescription = null,
//                    tint = DarkBlue
//                )
//            }
//            Spacer(Modifier.width(16.dp))
//            SearchField()
//        }


        Spacer(Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Use GridCells.Adaptive(minSize) for adaptive columns
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(8.dp) // Optional padding
        ) {
            items(dummyList) { item ->
                StockItem(
                    onClicked = {
                        // onOrderClicked?.invoke()
                    },
                    item = item
                )
            }
        }

        Spacer(Modifier.weight(1f))
        AppButton(
            modifier = Modifier.fillMaxWidth(.8f).align(Alignment.CenterHorizontally),
            text = "تأكيد الاستلام",
            onClick = {onConfirmClicked?.invoke()}
        )

    }
}

@Composable
private fun StockItem(
    item: HomeModel? = null,
    onClicked: (() -> Unit)? = null
) {
    Card(
        onClick = { onClicked?.invoke() },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(2.dp, WhiteGray)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.greco_product),
                contentDescription = null,
                modifier = Modifier
                    .size(84.dp)
                    .clip(
                        RoundedCornerShape(12.dp)
                    ),
                contentScale = ContentScale.Crop
            )



            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "اسم المنتج",
                    style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                )
                Text(
                    text = "البان",
                    style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
                )
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "الكمية",
                    style = CompactTypography.headlineLarge.copy(fontSize = 15.sp)
                )
                Text(
                    text = "1 كرتونة",
                    style = CompactTypography.headlineLarge.copy(fontSize = 15.sp),

                    )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "السعر",
                    style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                )
                Text(
                    text = "3100 EGP",
                    style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
                )
            }


        }

    }
}

@Composable
private fun SearchField(

) {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "بحث",
        label = "بحث",
        isError = false,
        errorMessage = null,
        value = "",
        onValueChange = {
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        trailingCompose = {
            Icon(imageVector = Icons.Default.Search, tint = MediumGray, contentDescription = "")
        }
    )
}
@Preview
@Composable
private fun ReceiveStockScreenPreview() {
    ReceiveStockScreen()
}