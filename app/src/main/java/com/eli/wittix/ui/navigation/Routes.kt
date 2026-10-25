package com.eli.wittix.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
data object ProductListRoute

@Serializable
data class ProductDetailRoute(val productId: Long)

@Serializable
data object CartRoute
