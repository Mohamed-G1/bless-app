package com.nat.couriersapp.base.ui.addImage

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nat.couriersapp.ui.theme.LightGray

@Composable
fun AddImage(
    modifier: Modifier = Modifier,
    color: Color = LightGray,
    imageUri: Uri? = null,
    onImageSelect: ((Uri) -> Unit)? = null,
    onCancelSelect: (() -> Unit)? = null,
) {

    var currentImageUri by remember {
        mutableStateOf(imageUri)
    }
    val shape = MaterialTheme.shapes.small
    var size by remember { mutableStateOf(IntSize.Zero) }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        if (it != null) {
            currentImageUri = it
            onImageSelect?.invoke(it)
        } else {
            onCancelSelect?.invoke()
        }
    }

    Box(Modifier.onGloballyPositioned { coordinates ->
        size = coordinates.size
    }) {
        if (currentImageUri != null) {
            PreviewImage(
                modifier, shape,
                photoPicker, currentImageUri
            )
        } else {
            AddAnImage(
                modifier, shape,
                photoPicker, color, size
            )
        }
    }

}

@Composable
private fun PreviewImage(
    modifier: Modifier,
    shape: CornerBasedShape,
    photoPicker: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    currentImageUri: Uri?
) {
    AsyncImage(
        modifier = modifier
            .fillMaxSize()
            .clip(shape)
            .clickable {
                launchPhotoPicker(photoPicker)
            },
        model = ImageRequest.Builder(LocalContext.current)
            .data(currentImageUri)
            .crossfade(enable = true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun AddAnImage(
    modifier: Modifier,
    shape: CornerBasedShape,
    photoPicker: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    color: Color,
    size: IntSize
) {
    Card(
        modifier
            .fillMaxSize()
            .clip(shape)
            .clickable {
                launchPhotoPicker(photoPicker)
            },
        border = BorderStroke(
            width = 1.dp,
            color = color
        ),
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Icon(
            Icons.Default.AddCircle,
            modifier = Modifier
                .fillMaxSize()
                .padding((size.width / 10).dp),
            tint = color,
            contentDescription = null
        )

    }
}


private fun launchPhotoPicker(photoPicker: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>) {
    photoPicker.launch(
        PickVisualMediaRequest(
            ActivityResultContracts.PickVisualMedia.ImageOnly
        )
    )
}

@Preview
@Composable
fun AddImagePreview() {
    AddImage()
}