package com.eli.wittix.screens.cart

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CartScreen(viewModel: CartViewModel = koinViewModel()) {
    Text("Cart")
}
