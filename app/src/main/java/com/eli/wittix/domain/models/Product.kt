package com.eli.wittix.domain.models

import kotlin.math.roundToInt

data class Product(
    val id: Long,
    val title: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val category: String,
    val thumbnail: String,
) {
    val hasDiscount get() = discountPercentage > 0
    val discountLabel get() = "-${discountPercentage.roundToInt()}%"

    // API price is already discounted; recover the price before discount.
    val originalPrice get() = price / (1 - discountPercentage / 100)
    val inStock get() = stock > 0
}
