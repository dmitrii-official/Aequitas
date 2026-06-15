package org.dmn.aequitas.ui.atoms

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorSnackbar(
    errorMessage: String,
) {
    Snackbar(
        containerColor = MaterialTheme.colorScheme.error,
        contentColor = MaterialTheme.colorScheme.onError,
    ) {
        Text(text = errorMessage)
    }
}

@Composable
fun SuccessSnackbar(
    message: String,
) {
    Snackbar(
        containerColor = MaterialTheme.colorScheme.outline,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {
        Text(text = message)
    }
}

class AequitasSnackbarVisuals(
    override val message: String,
    val isError: Boolean = false,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
): SnackbarVisuals