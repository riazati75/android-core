@file:Suppress("unused")

package ir.farsroidx.core.additives

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

// TODO: Coroutine =================================================================== Coroutine ===

fun coroutineExceptionHandler(
    block: (CoroutineContext, Throwable) -> Unit
): CoroutineExceptionHandler = CoroutineExceptionHandler { context, exception ->
    block(context, exception)
}

fun coroutineExceptionHandler(
    block: (Throwable) -> Unit
): CoroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
    block(exception)
}