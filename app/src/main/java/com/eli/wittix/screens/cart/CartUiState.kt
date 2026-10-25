package com.eli.wittix.screens.cart

import com.eli.wittix.domain.models.Cart

data class CartUiState(
    val cart: Cart = Cart(emptyList()),
    val isLoading: Boolean = false,
    val error: String? = null,
)
