package com.siad.stayksa.screens.onboarding.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.siad.stayksa.R
import com.siad.stayksa.screens.onboarding.presentation.components.DotIndicatorView
import com.siad.stayksa.screens.onboarding.presentation.components.NextOnboardingBtn

import com.siad.stayksa.screens.onboarding.presentation.components.SkipBtn
import com.siad.stayksa.screens.onboarding.presentation.components.onboardingPage.OnboardingPage
import com.siad.stayksa.ui.theme.DarkGreen
import com.siad.stayksa.ui.theme.LightGreen

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    state: OnBoardingState,
    onSkipClick: ((OnBoardingEvent) -> Unit)? = null,
    onLastNextClick: ((OnBoardingEvent) -> Unit)? = null,
    onExploreClick: (() -> Unit)? = null,
    onLoginClick: (() -> Unit)? = null
) {

    val pagerState = rememberPagerState(initialPage = 0) {
        state.model?.size ?: 0
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        val scope = rememberCoroutineScope()

        //Hide the skip button from the last image
        if (pagerState.currentPage != 2) {
            SkipBtn(
                onClick = {
                    onSkipClick?.invoke(OnBoardingEvent.SkipEvent)
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(fraction = .85f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.stay_ksa_logo),
                    contentDescription = null,
                )


                HorizontalPager(state = pagerState) { index ->
                    OnboardingPage(model = state.model?.get(index))
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                ButtonsSection(pagerState, scope, onSkipClick = {})

            }
        }
    }

}

@Composable
private fun ButtonsSection(
    pagerState: PagerState,
    scope: CoroutineScope,
    onSkipClick: (() -> Unit)?
) {
    // If this is the last page show sign in and explore buttons
    if (pagerState.currentPage != 2) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Show the back button only after the first page
            if (pagerState.currentPage != 0) {
                NextOnboardingBtn(
                    onClick = {
                        // if this is the first image dose not back
                        if (pagerState.currentPage != 0) {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    }, modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(vertical = 8.dp)
                )
            }
            DotIndicatorView(
                modifier = Modifier.align(Alignment.Center),
                numberOfPages = pagerState.pageCount,
                selectedPage = pagerState.currentPage,
                defaultColor = LightGreen,
                selectedColor = DarkGreen
            )

        }
    } else {
        SkipBtn(
            onClick = {
                onSkipClick?.invoke()
            }
        )
//        Column(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            AppButton(
//                text = stringResource(R.string.explore),
//                onClick = {
//                    onExploreClick?.invoke()
//                }
//            )
//
//            Spacer(modifier = Modifier.height(MaterialTheme.dimens._12Dp))
//
//            AppClickableText(
//                text = stringResource(id = R.string.sign_in),
//                textColor = Orange,
//                onClick = {
//                    onLoginClick?.invoke()
//                }
//            )
//        }
    }
}
//
//@Preview
//@Preview(locale = "en")
//@Composable
//fun PreviewOnboardingScreen() {
//    NewStayKSATheme {
//        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
//            OnboardingScreen(onBoardingDefaultState())
//        }
//    }
//}