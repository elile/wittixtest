package com.eli.wittix.ui.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier

// Provided by AppNavigation; null in previews, so the modifier becomes a no-op.
@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }
val LocalNavAnimatedScope = compositionLocalOf<AnimatedVisibilityScope?> { null }

// Morphs the product image between list card and detail hero.
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Modifier.sharedProductImage(productId: Long): Modifier {
    val shared = LocalSharedTransitionScope.current ?: return this
    val animated = LocalNavAnimatedScope.current ?: return this
    return with(shared) {
        sharedElement(rememberSharedContentState("image-$productId"), animated)
    }
}
