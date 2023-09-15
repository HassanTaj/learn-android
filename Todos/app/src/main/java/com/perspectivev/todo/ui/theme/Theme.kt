package com.perspectivev.todo.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val _darkColorScheme = darkColorScheme(

    primary = Color(0xFF366ACD),
    secondary = Color(0xFF1565C0),//PurpleGrey80,
    tertiary = Color(0xFF0D47A1),//Pink80,

    background = Color(0xFF000000),
    surface = Color(0xFF000000),

    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFFF6F6F6),
    onTertiary = Color(0xFFFFFFFF),
    onBackground = Color(0xFF5F5F5F),
    onSurface = Color(0xFF5F5F5F),
)

private val _lightColorScheme = lightColorScheme(
    primary = Color(0xFF0D47A1),
    secondary = Color(0xFF1565C0),
    tertiary = Color(0xFF1976D2),

//
//    background = Color(0xFFE8E7E8),
//    surface = Color(0xFFD8D8D8),
//    onPrimary = Color.Black,
//    onSecondary = Color.Black,
//    onTertiary = Color.Black,
//    onBackground = Color(0xFFF3F2F4),
//    onSurface = Color(0xFFFEFEFE),
)

@Composable
fun TodosTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> _darkColorScheme
        else -> _lightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}