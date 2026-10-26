package com.eli.wittix.ui.screens.productdetail

import androidx.annotation.StringRes
import com.eli.wittix.domain.models.ProductDetails

data class ProductDetailUiState(
    val product: ProductDetails? = null,
    val isLoading: Boolean = false,
    @StringRes val error: Int? = null,
)
