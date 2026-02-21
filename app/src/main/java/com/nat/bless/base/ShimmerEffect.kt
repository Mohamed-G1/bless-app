package com.nat.bless.base

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.random.Random

fun Modifier.shimmerEffect(
    colors : List<Color> = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    ),
    duration : Int = 1000
) : Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "")
    val translateAnimation by transition.animateFloat(
        initialValue = -size.width.toFloat(),
        targetValue = size.width.toFloat() * 2,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = duration,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "Shimmer loading animation",
    )

    val brush = Brush.linearGradient(
        colors = colors,
        start = Offset(translateAnimation, 0f),
        end = Offset(translateAnimation + size.width.toFloat(), 0f),
    )
    this
        .onGloballyPositioned { size = it.size }
        .background(brush)
}


@Composable
fun ShimmerLayout(
    isLoading: Boolean,
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    // Local state that applies a small delay before disabling shimmer
    var showShimmer by remember { mutableStateOf(isLoading) }

    // When isLoading switches from true → false, delay the content a bit
    LaunchedEffect(isLoading) {
        if (isLoading) {
            showShimmer = true
        } else {
            // Small random delay for smoother staggered transition
            delay(Random.nextInt(80, 250).toLong())
            showShimmer = false
        }
    }

    Crossfade(
        targetState = showShimmer,
        animationSpec = tween(durationMillis = 400),
        label = "shimmer crossfade"
    ) { loading ->
        if (loading) {
            Box(
                modifier = modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxSize()
                    .shimmerEffect()
            )
        } else {
            content()
        }
    }
}




