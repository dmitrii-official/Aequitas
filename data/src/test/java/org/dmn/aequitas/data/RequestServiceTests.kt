package org.dmn.aequitas.data

import io.mockk.every
import io.mockk.mockkObject
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
class RequestServiceTests {
    @Test
    fun `Request service approve() will fail 50 percent of time`() = runTest {
        mockkObject(Random)
        // Forcing Random to return false all the time, simulating 50% failure
        every { Random.nextBoolean() } returns false

        val subject = RequestServiceImpl()

        assertTrue(subject.approve().isFailure)
    }

    @Test
    fun `Request service approve() will succeed 50 percent of time`() = runTest {
        mockkObject(Random)
        // Forcing Random to return true all the time simulating 50% success
        every { Random.nextBoolean() } returns true

        val subject = RequestServiceImpl()

        assertTrue(subject.approve().isSuccess)
    }

    @Test
    fun `Request service approve() will delay for 2 sec`() = runTest {
        mockkObject(Random)
        every { Random.nextBoolean() } returns true

        val deferred = async { RequestServiceImpl().approve() }

        advanceTimeBy(2_000L.milliseconds)
        runCurrent()

        verify(exactly = 1) { Random.nextBoolean() }

        deferred.await()
    }

    @Test
    fun `Request service approve() will not delay for 1 sec`() = runTest {
        mockkObject(Random)
        every { Random.nextBoolean() } returns true

        val deferred = async { RequestServiceImpl().approve() }

        advanceTimeBy(1_000L.milliseconds)
        runCurrent()

        verify(exactly = 0) { Random.nextBoolean() }

        deferred.await()
    }

    @Test
    fun `Request service reject() will always succeed`() = runTest {
        val result = RequestServiceImpl().reject()

        assertTrue(result.isSuccess)
    }

}