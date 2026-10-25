package com.eli.wittix.screens.productdetail

import com.eli.wittix.domain.models.ProductDetails

data class ProductDetailUiState(
    val product: ProductDetails? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
