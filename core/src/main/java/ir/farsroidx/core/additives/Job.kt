package ir.farsroidx.core.additives

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job

fun Job?.forceCancel(cause: CancellationException? = null) {
    if (this != null && this.isActive && !this.isCompleted) {
        this.cancel(cause)
    }
}