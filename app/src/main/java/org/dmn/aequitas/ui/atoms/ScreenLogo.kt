package org.dmn.aequitas.ui.atoms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.dmn.aequitas.R

@Composable
fun ScreenLogo() {
    Image(
        painter = painterResource(R.drawable.screen_logo),
        contentDescription = "VIA logo",
        modifier = Modifier
            .padding(horizontal = 80.dp, vertical = 68.dp)
    )
}