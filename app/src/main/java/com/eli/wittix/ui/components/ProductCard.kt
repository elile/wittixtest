package com.eli.wittix.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eli.wittix.domain.models.Product
import com.eli.wittix.theme.ProductCatalogTheme

@Composable
fun ProductCard(product: Product, onClick: () -> Unit, modifier: Modifier = Modifier) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
    ) {
        ProductImage(
            product.thumbnail,
            contentDescription = product.title,
            modifier = Modifier.fillMaxWidth().height(140.dp),
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
private fun ProductCardPreview() = ProductCatalogTheme {
    ProductCard(Product(1, "Essence Mascara Lash Princess", 9.99, 7.17, 4.94, 5, "beauty", ""), onClick = {}, modifier = Modifier.width(180.dp))
}
