package com.eli.wittix.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.eli.wittix.theme.WittixTheme

// Grey box while loading, on error and in previews.
@Composable
fun ProductImage(url: String, contentDescription: String?, modifier: Modifier = Modifier) {
    val placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant)
    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        placeholder = placeholder,
        error = placeholder,
        fallback = placeholder,
    )
}

@Preview
@Composable
private fun ProductImagePreview() = WittixTheme { ProductImage("", contentDescription = null, Modifier.size(72.dp)) }
