/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.domain.models

data class CartItem(val product: Product, val quantity: Int) {
    val subtotal get() = product.price * quantity
}

data class Cart(val items: List<CartItem>) {
    val count get() = items.sumOf { it.quantity }
    val total get() = items.sumOf { it.subtotal }
}
