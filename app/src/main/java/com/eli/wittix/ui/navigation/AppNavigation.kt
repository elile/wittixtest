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
        composable<ProductListRoute> { ProductListScreen() }
        composable<ProductDetailRoute> { ProductDetailScreen() }
        composable<CartRoute> { CartScreen() }
    }
}
