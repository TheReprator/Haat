package dev.reprator.haat.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter.State.Error
import coil.compose.AsyncImagePainter.State.Loading
import coil.compose.rememberAsyncImagePainter
import com.vanniktech.blurhash.BlurHash
import dev.reprator.haat.R


@Composable
fun DynamicAsyncImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    placeholder: Painter = painterResource(R.drawable.icon_logo),
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val imageLoader = rememberAsyncImagePainter(
        model = imageUrl,
        onState = { state ->
            isLoading = state is Loading
            isError = state is Error
        },
    )
    val isLocalInspection = LocalInspectionMode.current
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        if (isLoading && !isLocalInspection) {
            // Display a progress bar while loading
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(80.dp),
                color = MaterialTheme.colorScheme.tertiary,
            )
        }
        Image(
            contentScale = ContentScale.Crop,
            painter = if (isError.not() && !isLocalInspection) imageLoader else placeholder,
            contentDescription = contentDescription,
        )
    }
}

@Composable
fun DynamicAsyncImage(
    imageUrl: String,
    contentDescription: String?,
    blurHash: String?,
    modifier: Modifier = Modifier,
    logoPlaceholder: Painter = painterResource(R.drawable.icon_logo),
) {
    var isError by remember { mutableStateOf(false) }

    val imageLoader = rememberAsyncImagePainter(
        model = imageUrl,
        onState = { state ->
            isError = state is Error
        },
    )

    val placeholder = remember(blurHash) {
        val bitmap = try {
            BlurHash.decode(blurHash!!, 200, 200)
        } catch (e: Exception) {
            null
        }

        bitmap?.asImageBitmap()?.let { BitmapPainter(it) }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        val painter = if (isError && placeholder != null) {
            placeholder
        } else if (isError) {
            logoPlaceholder
        } else {
            imageLoader
        }
        Image(
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = contentDescription
        )
    }
}