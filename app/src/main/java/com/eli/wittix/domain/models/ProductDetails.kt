/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.domain.models

data class ProductDetails(
    val product: Product,
    val description: String,
    val brand: String?,
    val images: List<String>,
    val categoryName: String? = null,
) {
    val heroImage get() = images.firstOrNull() ?: product.thumbnail
    // Falls back to slug until categories are cached.
    val categoryLabel get() = categoryName ?: product.category
}
