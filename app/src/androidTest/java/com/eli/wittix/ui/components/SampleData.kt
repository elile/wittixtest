package com.eli.wittix.ui.components

import com.eli.wittix.domain.models.CartItem
import com.eli.wittix.domain.models.Category
import com.eli.wittix.domain.models.Product

// Shared fixtures for UI tests.
internal object SampleData {
    val product = Product(
        id = 1,
        title = "Essence Mascara Lash Princess",
        price = 9.99,
        discountPercentage = 7.17,
        rating = 4.94,
        stock = 5,
        category = "beauty",
        thumbnail = "",
    )
    val outOfStock = product.copy(id = 2, title = "Eyeshadow Palette", discountPercentage = 0.0, stock = 0)
    val categories = listOf(
        Category("beauty", "Beauty"),
        Category("fragrances", "Fragrances"),
        Category("furniture", "Furniture"),
    )
    val cartItem = CartItem(product, quantity = 2)
}
