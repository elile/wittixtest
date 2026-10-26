package com.eli.wittix.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val Surface = Color(0xFFFFFFFF)
private val SurfaceVariant = Color(0xFFF1F3F4)

// Tokens from the assignment's design system.
val ProductCatalogColors = lightColorScheme(
    primary = Color(0xFF1A73E8),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD3E3FD),
    onPrimaryContainer = Color(0xFF001D36),
    secondary = Color(0xFF5F6368),
    secondaryContainer = Color(0xFFE8EAED),
    background = Color(0xFFFAFAFA),
    surface = Surface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = Color(0xFF49454F),
    onSurface = Color(0xFF1C1B1F),
    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFCAC4D0),
    error = Color(0xFFD93025),
    errorContainer = Color(0xFFFCE8E6),
    // Not in the doc; mapped to doc greys so M3 defaults don't leak purple.
    surfaceTint = Color.Transparent,
    surfaceContainerLowest = Surface,
    surfaceContainerLow = Surface,
    surfaceContainer = SurfaceVariant,
    surfaceContainerHigh = SurfaceVariant,
    surfaceContainerHighest = SurfaceVariant,
)

private val DarkSurface = Color(0xFF1E1F22)
private val DarkSurfaceVariant = Color(0xFF2D2F33)

// Dark counterpart derived from the light tokens (not in the doc).
val ProductCatalogDarkColors = darkColorScheme(
    primary = Color(0xFF8AB4F8),
    onPrimary = Color(0xFF002D62),
    primaryContainer = Color(0xFF0B57D0),
    onPrimaryContainer = Color(0xFFD3E3FD),
    secondary = Color(0xFFBDC1C6),
    secondaryContainer = Color(0xFF3C4043),
    background = Color(0xFF121314),
    surface = DarkSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = Color(0xFFCAC4D0),
    onSurface = Color(0xFFE3E3E3),
    outline = Color(0xFF938F99),
    outlineVariant = Color(0xFF49454F),
    error = Color(0xFFF28B82),
    errorContainer = Color(0xFF5C1A15),
    surfaceTint = Color.Transparent,
    surfaceContainerLowest = DarkSurface,
    surfaceContainerLow = DarkSurface,
    surfaceContainer = DarkSurfaceVariant,
    surfaceContainerHigh = DarkSurfaceVariant,
    surfaceContainerHighest = DarkSurfaceVariant,
)

// Rating star; not an M3 role, so exposed next to the scheme.
val ColorScheme.starAmber get() = Color(0xFFFFC107)
