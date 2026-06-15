package org.dmn.aequitas.viewmodel

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.dmn.aequitas.Approve
import org.dmn.aequitas.DetailsViewModel
import org.dmn.aequitas.Reject
import org.dmn.aequitas.UiState
import org.dmn.aequitas.data.RequestService
import org.dmn.aequitas.data.RequestServiceResult
import org.dmn.aequitas.data.RequestType
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTests {

    @JvmField
    @RegisterExtension
    val mainDispatcher = MainDispatcherExtension(StandardTestDispatcher())

    @Test
    fun `DetailsViewModel initial state`(): Unit = runTest {
        val service = mockk<RequestService>()
        val viewModel = DetailsViewModel(service)

        val state = viewModel.uiState.value
        assertFalse(state.loading)
        assertFalse(state.completed)
    }

    @Test
    fun `DetailsViewModel starts loading when intent is sent`() = runTest {
        val service = mockk<RequestService>()
        val viewModel = DetailsViewModel(service)

        coEvery { service.reject() } returns Result.success(RequestServiceResult(RequestType.Reject, "Request rejected"))

        val states = mutableListOf<UiState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.toList(states)
        }

        viewModel.sendIntent(Reject)
        advanceUntilIdle()

        assertTrue(states.any { it.loading })
        assertTrue(states.last().completed)
    }

    @Test
    fun `DetailsViewModel if approve fails then result is an ErrorRequest`() = runTest {
        val service = mockk<RequestService>()
        val viewModel = DetailsViewModel(service)

        coEvery { service.approve() } returns Result.failure(exception = IOException())

        val states = mutableListOf<UiState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.toList(states)
        }

        viewModel.sendIntent(Approve)
        advanceUntilIdle()

        assertTrue(states.last().requestResult is RequestResult.ErrorResult)
    }

    @Test
    fun `DetailsViewModel if approve succeeds then result has success set to true`() = runTest {
        val service = mockk<RequestService>()
        val viewModel = DetailsViewModel(service)

        coEvery { service.approve() } returns Result.success(RequestServiceResult(RequestType.Approve, ""))

        val states = mutableListOf<UiState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.toList(states)
        }

        viewModel.sendIntent(Approve)
        advanceUntilIdle()

        assertTrue(states.last().requestResult is RequestResult.ApproveRequest)

        val result = states.last().requestResult as RequestResult.ApproveRequest
        assertTrue(result.success)
    }
}