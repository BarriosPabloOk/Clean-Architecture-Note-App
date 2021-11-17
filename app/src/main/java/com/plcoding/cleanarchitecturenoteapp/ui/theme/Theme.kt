package com.plcoding.cleanarchitecturenoteapp.ui.theme

import androidx.compose.material.MaterialTheme
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

@Composable
fun CleanArchitectureNoteAppTheme(darkTheme: Boolean = true, content: @Composable() () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}