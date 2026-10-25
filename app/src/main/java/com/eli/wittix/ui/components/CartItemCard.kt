package com.eli.wittix.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eli.wittix.domain.models.CartItem
import com.eli.wittix.domain.models.Product
import com.eli.wittix.theme.ProductCatalogTheme

@Composable
fun CartItemCard(
    item: CartItem,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            ProductImage(
                item.product.thumbnail,
                contentDescription = null,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp)),
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    item.product.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    item.product.price.asPrice(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                QuantityStepper(item.quantity, onDecrease, onIncrease)
            }
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Delete, contentDescription = "Remove ${item.product.title}")
            }
        }
    }
}

@Preview
@Composable
private fun CartItemCardPreview() = ProductCatalogTheme {
    CartItemCard(
        CartItem(
            Product(
                1,
                "Essence Mascara Lash Princess",
                9.99,
                7.17,
                4.94,
                5,
                "beauty",
                ""
            ), quantity = 2
        ), onDecrease = {}, onIncrease = {}, onRemove = {})
}
