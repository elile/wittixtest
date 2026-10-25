package com.eli.wittix.screens.productlist

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductListScreen(viewModel: ProductListViewModel = koinViewModel()) {
    Text("ProductList")
}
