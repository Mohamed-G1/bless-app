package com.nat.greco.base.ui.addImage

import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nat.greco.BuildConfig


@Composable
fun AddProfileImage(
    modifier: Modifier = Modifier,
    imageUri: Uri? = null,
    onImageSelect: ((Uri) -> Unit)? = null,
) {

    var currentImageUri by remember { mutableStateOf<Uri?>(null) }

    var initialImageUri by remember { mutableStateOf(imageUri) }

    LaunchedEffect(key1 = imageUri) {
        initialImageUri = imageUri
    }

    var size by remember { mutableStateOf(IntSize.Zero) }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {

        if (it != null) {
            Log.d("AddProfileImage", "New photo selected: $it")
            currentImageUri = it
            onImageSelect?.invoke(it)
        }
    }

    Box(Modifier.onGloballyPositioned { coordinates ->
        size = coordinates.size

    }) {
        if (currentImageUri != null) {
            PreviewImage(
                modifier,
                photoPicker,
                currentImageUri
            )
        } else if (initialImageUri != null) {
            InitialPreviewImage(
                modifier = modifier,
                photoPicker = photoPicker,
                initialImageUri = initialImageUri
            )
        } else {
            AddAnImage(
                modifier,
                photoPicker
            )
        }
    }

}

@Composable
private fun InitialPreviewImage(
    modifier: Modifier,
    photoPicker: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    initialImageUri: Uri?
) {

    val baseUrl =  BuildConfig.BASE_URL

    AsyncImage(
        modifier = modifier
            .clickable { launchPhotoPicker(photoPicker) },
        model = ImageRequest.Builder(LocalContext.current)
            .data("$initialImageUri")
            .crossfade(enable = true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun PreviewImage(
    modifier: Modifier,
    photoPicker: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    currentImageUri: Uri?
) {
    AsyncImage(
        modifier = modifier
            .clip(CircleShape)
            .clickable { launchPhotoPicker(photoPicker) },
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
    photoPicker: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
) {

    Surface(
        modifier
            .clip(CircleShape)
            .clickable {
                launchPhotoPicker(photoPicker)
            },
        shape = CircleShape,
        color = Color.White
    ) {}
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
fun ProfileImagePreview() {
    AddProfileImage(imageUri = "".toUri())
}