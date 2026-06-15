package org.dmn.aequitas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.dmn.aequitas.data.RequestService
import org.dmn.aequitas.data.RequestServiceResult
import org.dmn.aequitas.data.RequestType
import org.dmn.aequitas.viewmodel.RequestResult
import timber.log.Timber

data class UiState(
    val loading: Boolean = false,
    val completed: Boolean = false,
    val requestResult: RequestResult = RequestResult.RejectRequest("")
)

sealed interface ActionIntent
object Approve : ActionIntent
object Reject : ActionIntent

class DetailsViewModel(
    val requestService: RequestService
) : ViewModel() {
    private val timber = Timber.tag(this::class.java.name)

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun sendIntent(intent: ActionIntent) {
        timber.d("sendIntent($intent)")
        viewModelScope.launch {
            _uiState.emit(_uiState.value.copy(loading = true))
            when (intent) {
                Approve -> approve()
                Reject -> reject()
            }
        }
    }

    private fun approve() {
        timber.d("approve()")
        viewModelScope.launch {
            val result = requestService.approve()
            timber.d("result = $result")
            processResult(result)
        }
    }

    private fun reject() {
        timber.d("reject()")
        viewModelScope.launch {
            val result = requestService.reject()
            timber.d("result = $result")
            processResult(result)
        }
    }

    private suspend fun processResult(result: Result<RequestServiceResult>) {
        when {
            result.isSuccess -> {
                _uiState.emit(
                    _uiState.value.copy(
                        loading = false,
                        completed = true,
                        requestResult = result.getOrNull()?.let {
                            if (it.type == RequestType.Approve)
                                RequestResult.ApproveRequest(success = true, errorMessage = "Request approved")
                            else
                                RequestResult.RejectRequest(errorMessage = it.errorMessage)
                        } ?: RequestResult.ErrorResult(errorMessage = "Service Failed")
                    )
                )
            }

            result.isFailure -> {
                _uiState.emit(
                    _uiState.value.copy(
                        loading = false,
                        completed = true,
                        requestResult = result.getOrNull()?.let {
                            if (it.type == RequestType.Approve)
                                RequestResult.ApproveRequest(success = false, errorMessage = "Request approval failed.")
                            else
                                RequestResult.RejectRequest(errorMessage = "Request rejection failed.")
                        } ?: RequestResult.ErrorResult(
                            errorMessage = "Service Failed"
                        )
                    )
                )
            }
        }
    }
}
