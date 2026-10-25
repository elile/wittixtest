package com.eli.wittix.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eli.wittix.domain.models.Product
import com.eli.wittix.theme.WittixTheme

@Composable
fun ProductCard(product: Product, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(onClick = onClick, modifier = modifier) {
        ProductImage(
            product.thumbnail,
            contentDescription = product.title,
            modifier = Modifier.fillMaxWidth().aspectRatio(1f),
        )
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                product.title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            PriceRow(product)
            RatingRow(product.rating)
        }
    }
}

@Preview
@Composable
private fun ProductCardPreview() = WittixTheme {
    ProductCard(Product(1, "Essence Mascara Lash Princess", 9.99, 7.17, 4.94, 5, "beauty", ""), onClick = {}, modifier = Modifier.width(180.dp))
}
