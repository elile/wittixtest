package com.eli.wittix.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eli.wittix.screens.cart.CartScreen
import com.eli.wittix.screens.productdetail.ProductDetailScreen
import com.eli.wittix.screens.productlist.ProductListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = ProductListRoute) {
        composable<ProductListRoute> {
            ProductListScreen(
                onProductClick = { navController.navigate(ProductDetailRoute(it)) },
                onCartClick = { navController.navigate(CartRoute) },
            )
        }
        // ProductDetailViewModel reads productId from the route via SavedStateHandle.
        composable<ProductDetailRoute> { ProductDetailScreen(onBack = navController::navigateUp) }
        composable<CartRoute> { CartScreen(onBack = navController::navigateUp) }
    }
}
