package com.eli.wittix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.eli.wittix.theme.ProductCatalogTheme
import com.eli.wittix.ui.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Default style flips bar icons with system dark mode.
        enableEdgeToEdge()
        setContent { ProductCatalogTheme { AppNavigation() } }
    }
}
