package com.plcoding.cleanarchitecturenoteapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = GreenApp,
    onPrimary = DarkGray,
    primaryVariant= GreenAppVariant,

    background = LightGreenBackGround,
    onBackground = DarkGray,

    surface = LightGreenBackGround,
    onSurface = DarkGray
)

private val DarkColorPalette = darkColors(
    primary = DarkGreenApp,
    onPrimary = LightGray,
    primaryVariant = DarkGreenVariant,

    background = DarkGreenBackGround,
    onBackground = LightGray,

    surface = DarkGreenBackGround,
    onSurface = LightGray
)


@Composable
fun CleanArchitectureNoteAppTheme(darkTheme: Boolean = false, content: @Composable() () -> Unit) {
    MaterialTheme(
        colors = if (!darkTheme) LightColorPalette else DarkColorPalette ,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}