package org.dmn.aequitas.ui.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.dmn.aequitas.R
import org.dmn.aequitas.ui.atoms.AequitasButton
import org.dmn.aequitas.ui.theme.AequitasTheme

@Composable
fun RequestActionPanel(
    onRequestApprove: () -> Unit,
    onRequestReject: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isApproved by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        AequitasButton(
            stringResource(R.string.reject),
            onClick = onRequestReject,
            modifier = Modifier
                .weight(0.3f)
                .fillMaxWidth()
        )
        Spacer(
            modifier = Modifier
                .weight(0.1f)
                .widthIn(min = 24.dp)
        )
        SlideSwitch(
            checked = isApproved,
            text = "Approve",
            modifier = Modifier
                .weight(0.6f)
                .fillMaxWidth(),
            onSwitch = { checked ->
                isApproved = checked
                onRequestApprove.invoke()
            }
        )
    }
}

@Preview
@Composable
fun `Request Action Panel preview`() {
    AequitasTheme(darkTheme = true, dynamicColor = false) {
        RequestActionPanel(
            onRequestApprove = { },
            onRequestReject = { },
            modifier = Modifier.padding(16.dp)
        )
    }
}