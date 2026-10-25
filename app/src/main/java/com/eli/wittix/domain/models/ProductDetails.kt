package com.eli.wittix.domain.models

data class ProductDetails(
    val product: Product,
    val description: String,
    val brand: String?,
    val images: List<String>,
) {
    val heroImage get() = images.firstOrNull() ?: product.thumbnail
}
