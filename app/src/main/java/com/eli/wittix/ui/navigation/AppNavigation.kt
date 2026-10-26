package com.eli.wittix.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eli.wittix.ui.components.LocalNavAnimatedScope
import com.eli.wittix.ui.components.LocalSharedTransitionScope
import com.eli.wittix.ui.screens.cart.CartScreen
import com.eli.wittix.ui.screens.productdetail.ProductDetailScreen
import com.eli.wittix.ui.screens.productlist.ProductListScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    SharedTransitionLayout {
        CompositionLocalProvider(LocalSharedTransitionScope provides this) {
            NavHost(navController, startDestination = ProductListRoute) {
                composable<ProductListRoute> {
                    CompositionLocalProvider(LocalNavAnimatedScope provides this) {
                        ProductListScreen(
                            onProductClick = { navController.navigate(ProductDetailRoute(it)) },
                            onCartClick = { navController.navigate(CartRoute) },
                        )
                    }
                }
                // ProductDetailViewModel reads productId from the route via SavedStateHandle.
                composable<ProductDetailRoute> {
                    CompositionLocalProvider(LocalNavAnimatedScope provides this) {
                        ProductDetailScreen(onBack = navController::navigateUp)
                    }
                }
                composable<CartRoute> { CartScreen(onBack = navController::navigateUp) }
            }
        }
    }
}
