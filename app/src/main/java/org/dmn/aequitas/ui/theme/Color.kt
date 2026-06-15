package org.dmn.aequitas.ui.theme

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val paleBlue = Color(0xFF_EFFCFF)
val blue = Color(0xFF_87E1ED)
val paleGreen = Color(0xFF_87D6CD)
val paleGreen2 = Color (0xFF_87D6A5)
val darkBlue = Color(0xFF_286676)
val darkerBlue = Color(0xFF_285976)
val darkerBlue2 = Color(0xFF_165868)
val almostBlack = Color(0xFF_0B2545)
val rose = Color(0xFF_FFABAB)

@Preview
@Composable
fun `Colors Preview`() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        CustomSurface(blue)
        CustomSurface(paleGreen)
        CustomSurface(paleGreen2)
        CustomSurface(darkBlue)
        CustomSurface(darkerBlue)
        CustomSurface(darkerBlue2)
        CustomSurface(almostBlack)
        CustomSurface(rose)
    }
}

@Composable
fun CustomSurface(color: Color) {
    Surface(color = color,
        modifier = Modifier
            .fillMaxHeight()
            .width(50.dp)
    ) {}
}
