package org.dmn.aequitas.ui.templates

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.dmn.aequitas.Approve
import org.dmn.aequitas.DetailsViewModel
import org.dmn.aequitas.R
import org.dmn.aequitas.Reject
import org.dmn.aequitas.navigation.HomeNavDestination
import org.dmn.aequitas.ui.atoms.Loading
import org.dmn.aequitas.ui.atoms.ScreenTitle
import org.dmn.aequitas.ui.molecules.RequestActionPanel
import org.dmn.aequitas.ui.molecules.RequestInfoPanel
import org.dmn.aequitas.ui.theme.AequitasTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Details(
    navController: NavController,
) {
    val viewModel: DetailsViewModel = koinViewModel()
    val viewState = viewModel.uiState.collectAsState()

    LaunchedEffect(viewState.value.completed) {
        if (viewState.value.completed) {
            if (navController.previousBackStackEntry != null) {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(HomeNavDestination.ResultKey, viewState.value.requestResult)
                navController.popBackStack()
            }
        }
    }

    when {
        viewState.value.loading -> Loading()
        else -> DetailsLayout(
            onRequestApprove = { viewModel.sendIntent(Approve) },
            onRequestReject = { viewModel.sendIntent(Reject) },
        )
    }
}

@Composable
fun DetailsLayout(
    onRequestApprove: () -> Unit,
    onRequestReject: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 40.dp, vertical = 64.dp)
    ) {
        val modifier = Modifier.padding(vertical = 16.dp)

        ScreenTitle(text = stringResource(R.string.new_request_screen_title),)
        RequestInfoPanel(modifier)
        RequestActionPanel(
            onRequestReject = onRequestReject,
            onRequestApprove = onRequestApprove,
            modifier = modifier,
        )
    }
}

@Preview
@Composable
fun `Details Preview`() {
    AequitasTheme(darkTheme = true, dynamicColor = false) {
        DetailsLayout(
            onRequestApprove = { },
            onRequestReject = { },
        )
    }
}