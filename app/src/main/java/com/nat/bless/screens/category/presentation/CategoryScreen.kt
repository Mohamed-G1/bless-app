package com.nat.bless.screens.category.presentation

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.bless.R
import com.nat.bless.base.ui.appLoading.FullLoading
import com.nat.bless.base.ui.imageLoader.AppImageLoading
import com.nat.bless.base.ui.toast.ShowToast
import com.nat.bless.screens.category.domain.models.CategoryResponse
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.ui.theme.Gray
import com.nat.bless.ui.theme.WhiteGray

@Composable
fun CategoryScreen(
    customerId: Int,
    state: CategoryState,
    onBackClicked: (() -> Unit)? = null,
    navigateToProducts: ((Int, Int) -> Unit)? = null,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = 24.dp,
                horizontal = 16.dp
            )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "الفئات",
                style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
            )

            IconButton(onClick = { onBackClicked?.invoke() }) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Use GridCells.Adaptive(minSize) for adaptive columns
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 80.dp
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(8.dp) // Optional padding
        ) {

            items(state.data) { item ->
                CategoryItem(
                    item = item,
                    onClicked = { id ->
                        navigateToProducts?.invoke(customerId, id)
                    },
                )
            }
        }

    }

    if (state.message.isNotEmpty()) {
        ShowToast(state.message)

    }

    if (state.isLoading) {
        FullLoading()
    }

}


@Composable
fun CategoryItem(item: CategoryResponse, onClicked: (Int) -> Unit) {
    Card(
        onClick = {
            onClicked.invoke(item.id ?: 0)
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = White),
        border = BorderStroke(2.dp, WhiteGray)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            AppImageLoading(
                imgUrl = item.image_url.orEmpty(),
                modifier = Modifier
                    .size(100.dp)
                    .clip(
                        RoundedCornerShape(12.dp)
                    ),
                contentScale = ContentScale.Fit
            )
            Text(
                text = item.name.orEmpty(),
                style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }
    }


}

@Preview
@Composable
private fun CategoryScreenPerview() {
    CategoryScreen(0, CategoryState())
}