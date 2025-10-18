package com.nat.greco.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.greco.R
import com.nat.greco.base.ui.appButton.AppButton
import com.nat.greco.base.ui.textField.AppTextField
import com.nat.greco.screens.home.domain.models.HomeModel
import com.nat.greco.screens.home.presentation.HomeEvents
import com.nat.greco.screens.home.presentation.HomeState.Companion.dummyList
import com.nat.greco.screens.home.presentation.components.SortBottomSheetLayout
import com.nat.greco.ui.theme.CompactTypography
import com.nat.greco.ui.theme.Gray
import com.nat.greco.ui.theme.MediumBlue
import com.nat.greco.ui.theme.MediumGray
import com.nat.greco.ui.theme.WhiteGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Products(
    onBackClicked: (() -> Unit)? = null, navigateToProductDetails: (() -> Unit)? = null
) {

    var openUnitOfMeasueSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    Column (
        modifier = Modifier  .padding(vertical = 16.dp, horizontal = 16.dp)
    ) {

        Column{

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "المنتجات",
                    style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
                )

                IconButton(onClick = { onBackClicked?.invoke() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = null
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            SearchField()

            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // Use GridCells.Adaptive(minSize) for adaptive columns
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(8.dp) // Optional padding
            ) {
                items(4) { item ->
                    ProductItem(
                        onClicked = {
                            openUnitOfMeasueSheet = true
                        },
                    )
                }
            }




        }


        Spacer(Modifier.weight(1f))
        AppButton(
            text = "تفاصيل المنتجات",
            onClick = {
                navigateToProductDetails?.invoke()
            },
        )


    }


        if (openUnitOfMeasueSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    openUnitOfMeasueSheet = false
                }, sheetState = sheetState, containerColor = White
            ) {
                UnitOfMeasureSheet(
                    onAddClicked = {
                        openUnitOfMeasueSheet = false
                    }
                )
            }

        }

}

@Composable
private fun ProductItem(
    onClicked: (() -> Unit)? = null
) {
    Card(
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
                painter = painterResource(R.drawable.splash_bg),
                contentDescription = null,
                modifier = Modifier
                    .size(84.dp)
                    .clip(
                        RoundedCornerShape(12.dp)
                    ),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "البان",
                style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Text(
                text = "3100 EGP",
                style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )


            Card(
                onClick = {
                    onClicked?.invoke()
                },
                colors = CardDefaults.cardColors(containerColor = MediumBlue)
            ) {
                Icon(imageVector =  Icons.Default.Add, modifier = Modifier.padding(6.dp),tint = White, contentDescription = null)

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
@Preview(showBackground = true)
@Composable
private fun ProductsPreview() {
    Products()
}