package com.nat.couriersapp.base.ui.imageLoader

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.nat.couriersapp.R

@Composable
fun AppImageLoading(
    modifier: Modifier = Modifier,
    imgUrl: String,
    contentScale: ContentScale = ContentScale.Crop
) {
    // Use AsyncImagePainter to handle the image loading states
    val painter = rememberAsyncImagePainter(
        model = imgUrl,
        error = painterResource(id = R.drawable.ic_error) // Your error image
    )

    // Observe the painter state for loading, success, or error
    val painterState = painter.state


    Box(modifier = modifier.fillMaxSize()) {
        // Show the image once it's loaded
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale
        )

        // Show loading indicator while the image is loading
        if (painterState is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Optionally handle the error state if needed
        if (painterState is AsyncImagePainter.State.Error) {
            // The error image will already be shown by Coil, but you could handle additional UI here
            Image(
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = contentScale
            )
        }
    }

}


@Preview
@Composable
fun AppImageLoadingPreview() {
    AppImageLoading(
        imgUrl = "",
    )
}