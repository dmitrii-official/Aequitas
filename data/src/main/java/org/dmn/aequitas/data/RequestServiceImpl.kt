package org.dmn.aequitas.data

import kotlinx.coroutines.delay
import timber.log.Timber
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds


class RequestServiceImpl: RequestService {

    companion object {
        private const val DELAY = 2_000L
    }

    val timber = Timber.tag(this::class.java.name)

    override suspend fun approve(): Result<RequestServiceResult> {
        timber.d("approve()")
        delay(DELAY.milliseconds)

        return if (Random.nextBoolean()) {
            timber.d("approve(result = success)")
            Result.success(RequestServiceResult(
                type = RequestType.Approve,
                errorMessage = "",
            ))
        } else {
            timber.d("approve(result = failure)")
            Result.failure(Exception("Failed"))
        }
    }

    override suspend fun reject(): Result<RequestServiceResult> {
        timber.d("reject()")
        return Result.success(RequestServiceResult(
            type = RequestType.Reject,
            errorMessage = "Request rejected"
        ))
    }
}