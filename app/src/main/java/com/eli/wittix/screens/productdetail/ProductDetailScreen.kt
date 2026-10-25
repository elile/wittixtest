package com.eli.wittix.screens.productdetail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductDetailScreen(viewModel: ProductDetailViewModel = koinViewModel()) {
    Text("ProductDetail")
}
