/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.ui.screens.cart

import androidx.annotation.StringRes
import com.eli.wittix.domain.models.Cart

data class CartUiState(
    val cart: Cart = Cart(emptyList()),
    val isLoading: Boolean = false,
    @StringRes val error: Int? = null,
)
