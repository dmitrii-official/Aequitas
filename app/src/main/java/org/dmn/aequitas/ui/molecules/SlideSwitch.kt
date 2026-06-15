package org.dmn.aequitas.ui.molecules

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.dmn.aequitas.R
import org.dmn.aequitas.ui.theme.AequitasTheme
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun SlideSwitch(
    checked: Boolean,
    text: String,
    onSwitch: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    colorOn: Color = MaterialTheme.colorScheme.outline,
    colorOff: Color = MaterialTheme.colorScheme.primary,
) {
    BoxWithConstraints(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier.background(Color.Transparent),
    ) {
        val scope = rememberCoroutineScope()
        val maxOffset = with(LocalDensity.current) { (maxWidth - 54.dp).toPx() }
        val thumbWidth = with(LocalDensity.current) { 54.dp.toPx() }
        val thumbOffset = remember { Animatable(if (checked) maxOffset else 0f) }
        var thumbOn by remember { mutableStateOf(checked) }

        Box(
            Modifier
                .matchParentSize()
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        scope.launch {
                            thumbOffset.snapTo((thumbOffset.value + delta).coerceIn(0f, maxOffset))
                        }
                    },
                    onDragStopped = { velocity ->
                        val flingsRight = velocity > 0
                        val pastMidpoint = thumbOffset.value > maxOffset / 2
                        val target = if (abs(velocity) > 100f) flingsRight else pastMidpoint

                        scope.launch {
                            thumbOffset.animateTo(
                                targetValue = if (target) maxOffset else 0f,
                                initialVelocity = velocity,
                            )
                            thumbOn = target
                            onSwitch(target)
                        }
                    }
                )
                .toggleable(
                    value = checked,
                    enabled = true,
                    role = Role.Switch,
                    onValueChange = { target ->
                        scope.launch {
                            thumbOffset.animateTo(if (target) maxOffset else 0f)
                            thumbOn = target
                            onSwitch(target)
                        }
                    },
                )

        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .drawBehind {
                    drawRoundRect(color = colorOff, cornerRadius = CornerRadius(12.dp.roundToPx().toFloat()))
                    val fillWidth = thumbOffset.value + thumbWidth / 2f
                    clipRect(right = fillWidth) {
                        drawRoundRect(color = colorOn, cornerRadius = CornerRadius(12.dp.roundToPx().toFloat()))
                    }
                }

        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.align(Alignment.Center)
            )

        }
        Box(
            modifier = Modifier
                .offset { IntOffset(x = thumbOffset.value.roundToInt(), y = 0) }
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(16.dp)
                )
                .size(54.dp)
        ) {
            Image(
                painter = painterResource(if (thumbOn) R.drawable.ic_checkmark_circle else R.drawable.ic_chevrons_right),
                contentDescription = "A thumb for slider",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
                    .fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
fun `Slideswitch Preview`() {

    AequitasTheme(darkTheme = true, dynamicColor = false) {
        SlideSwitch(
            checked = false,
            text = "Slide to approve",
            onSwitch = { }
        )
    }
}

@Preview
@Composable
fun `Slideswitch On Preview`() {

    AequitasTheme(darkTheme = true, dynamicColor = false) {
        SlideSwitch(
            checked = true,
            text = "Slide to approve",
            onSwitch = { }
        )
    }
}
