package com.eli.wittix.data.repositories

import kotlin.coroutines.cancellation.CancellationException

// Like runCatching, but keeps coroutine cancellation working.
internal suspend fun <T> safeCall(block: suspend () -> T): Result<T> =
    try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
