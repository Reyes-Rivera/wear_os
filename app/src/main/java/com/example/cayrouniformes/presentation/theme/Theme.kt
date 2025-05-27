package com.example.cayrouniformes.presentation.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.MaterialTheme

private val CayroWearColorPalette = Colors(
    primary = CayroBlue,
    primaryVariant = CayroBlueDark,
    secondary = CayroGold,
    secondaryVariant = CayroGoldDark,
    error = CayroRed,
    onPrimary = CayroTextPrimary,
    onSecondary = CayroBackgroundDark,
    onError = CayroTextPrimary,
    background = CayroBackgroundDark,
    onBackground = CayroTextPrimary,
    surface = CayroSurfaceDark,
    onSurface = CayroTextPrimary
)

@Composable
fun CayroUniformesTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = CayroWearColorPalette,
        typography = CayroTypography,
        content = content
    )
}