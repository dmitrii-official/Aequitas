package org.dmn.aequitas.ui.templates

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.dmn.aequitas.R
import org.dmn.aequitas.navigation.DetailsNavDestination
import org.dmn.aequitas.navigation.HomeNavDestination
import org.dmn.aequitas.ui.atoms.AequitasButton
import org.dmn.aequitas.ui.atoms.AequitasSnackbarVisuals
import org.dmn.aequitas.ui.atoms.ErrorSnackbar
import org.dmn.aequitas.ui.atoms.ScreenLogo
import org.dmn.aequitas.ui.atoms.ScreenTitle
import org.dmn.aequitas.ui.atoms.SuccessSnackbar
import org.dmn.aequitas.ui.theme.AequitasTheme
import org.dmn.aequitas.viewmodel.RequestResult

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(
    navController: NavController,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(navBackStackEntry) {
        val savedStateHandle = navBackStackEntry?.savedStateHandle ?: return@LaunchedEffect
        savedStateHandle.getStateFlow<RequestResult?>(HomeNavDestination.ResultKey, null)
            .collect { r ->
                r?.let { r ->
                    val visuals = when (r) {
                        is RequestResult.ApproveRequest -> {
                            if (r.success) AequitasSnackbarVisuals(isError = false, message = r.errorMessage)
                            else AequitasSnackbarVisuals(isError = true, message = r.errorMessage)
                        }
                        else -> AequitasSnackbarVisuals(isError = true, message = r.errorMessage)
                    }
                    snackbarHostState.showSnackbar(visuals)
                    savedStateHandle[HomeNavDestination.ResultKey] = null
                }
            }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(bottom = 48.dp, start = 20.dp, end = 20.dp)
            ) { data ->
                val visuals = data.visuals
                if (visuals is AequitasSnackbarVisuals) {
                    if (visuals.isError)
                        ErrorSnackbar(visuals.message)
                    else
                        SuccessSnackbar(visuals.message)
                }
            }
        }
    ) {
        HomeScreen {
            navController.navigate(DetailsNavDestination)
        }
    }
}


@Composable
fun HomeScreen(
    onNewRequest: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = 40.dp, vertical = 64.dp)
    ) {
        ScreenTitle(text = stringResource(R.string.home_screen_title))
        ScreenLogo()
        AequitasButton(
            text = stringResource(R.string.create_request),
            onClick = onNewRequest,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun `Home Preview`() {
    AequitasTheme(darkTheme = false, dynamicColor = false) {
        HomeScreen { }
    }
}