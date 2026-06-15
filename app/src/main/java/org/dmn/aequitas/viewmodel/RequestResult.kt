package org.dmn.aequitas.viewmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface RequestResult: Parcelable {
    val errorMessage: String

    @Parcelize
    data class EmptyResult(
        override val errorMessage: String = ""
    ): RequestResult

    @Parcelize
    data class ErrorResult(
        override val errorMessage: String,
    ) : RequestResult

    @Parcelize
    data class RejectRequest(
        override val errorMessage: String,
    ) : RequestResult

    @Parcelize
    data class ApproveRequest(
        val success: Boolean,
        override val errorMessage: String,
    ) : RequestResult
}