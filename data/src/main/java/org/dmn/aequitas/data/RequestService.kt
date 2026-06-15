package org.dmn.aequitas.data

interface RequestService {
    suspend fun approve(): Result<RequestServiceResult>
    suspend fun reject(): Result<RequestServiceResult>
}

enum class RequestType {
    Approve,
    Reject,
}
data class RequestServiceResult(
    val type: RequestType,
    val errorMessage: String,
)