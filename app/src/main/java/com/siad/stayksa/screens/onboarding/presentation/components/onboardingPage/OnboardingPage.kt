package com.siad.stayksa.screens.onboarding.presentation.components.onboardingPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.siad.stayksa.R
import com.siad.stayksa.base.ui.shimmer.shimmerLoading
import com.siad.stayksa.screens.onboarding.domain.models.OnBoardingResponse
import com.siad.stayksa.ui.theme.CompactTypography
import com.siad.stayksa.ui.theme.StayKsaTheme
import kotlinx.coroutines.delay

@Composable
fun OnboardingPage(
    model: OnBoardingResponse? = null,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ) {
//        AppImageLoading(
//            imgUrl = model?.image.orEmpty(),
//            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight(0.75f)
//                .clip(CircleShape),
//            contentScale = ContentScale.Crop,
//        )

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)

                    .graphicsLayer {
                        scaleX = -1f // Flips horizontally
                        transformOrigin = TransformOrigin(0.5f, 0.5f) // Pivot at center
                    }
                    .align(Alignment.TopStart)
                    .shimmerLoading(),
                contentScale = ContentScale.Crop,

                )

            Column(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(top = 120.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Let’s have the best vacation with us",
                    style = CompactTypography.headlineLarge,
                    modifier = Modifier.fillMaxWidth(.35f).shimmerLoading()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. ",
                    style = CompactTypography.labelMedium.copy(color = Color.Gray),
                    modifier = Modifier.fillMaxWidth().shimmerLoading()
                )
            }

        }

//        Text(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp),
//            text = model?.title.orEmpty(),
//            textAlign = TextAlign.Center,
//            style = CompactTypography.headlineMedium
//        )
//
//
//        Text(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp),
//            text = model?.description.orEmpty(),
//            textAlign = TextAlign.Center,
//            style = CompactTypography.bodyMedium.copy(color = Color.LightGray)
//        )
    }
}

@Preview(showBackground = true, locale = "en")
@Preview(showBackground = true, locale = "ar")
@Composable
fun OnBoardingPagePreview() {
    StayKsaTheme {
        OnboardingPage()
    }
}