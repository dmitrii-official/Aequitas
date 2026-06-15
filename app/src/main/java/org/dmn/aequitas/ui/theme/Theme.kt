package org.dmn.aequitas.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun AequitasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Preview
@Composable
fun `Light Scheme Preview`() {
    AequitasTheme(darkTheme = false, dynamicColor = false) {
        SchemePreview(MaterialTheme.colorScheme)
    }
}

@Preview
@Composable
fun `Dark Scheme Preview`() {
    AequitasTheme(darkTheme = true, dynamicColor = false) {
        SchemePreview(MaterialTheme.colorScheme)
    }
}

@Composable
fun SchemePreview(colorScheme: ColorScheme) {
    Column(
        modifier = Modifier.padding(24.dp)
    ) {
        Surface(
            modifier = Modifier
                .background(colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .padding(all = 24.dp)
            ) {
                Text(
                    text = "On Background",
                    color = colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge,
                )
                Box(
                    modifier = Modifier
                        .background(colorScheme.primary)
                        .border(width = 1.dp, color = colorScheme.outline)
                        .height(40.dp)
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "On Primary",
                        color = colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        }
    }
}