package org.dmn.aequitas.ui.atoms

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun ScreenTitle(
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.displayLarge,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        fontStyle = textStyle.fontStyle,
        fontSize = textStyle.fontSize,
        fontFamily = textStyle.fontFamily,
        fontWeight = textStyle.fontWeight,
        color = MaterialTheme.colorScheme.onBackground,
    )
}