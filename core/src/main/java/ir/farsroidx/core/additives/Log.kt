@file:Suppress("unused")

package ir.farsroidx.core.additives

import android.util.Log

// TODO: Log =============================================================================== Log ===

private const val logStrTag = "CentralCore"

fun vLog(vararg logs: Any?, isActive: Boolean = true) {
    if (isActive) {
        logs.forEach { log ->
            if ( log != null ) {
                Log.v(logStrTag, log.toString())
            } else {
                Log.v(logStrTag, ".: === LOG_IS_NULL === :.")
            }
        }
    }
}

fun iLog(vararg logs: Any?, isActive: Boolean = true) {
    if (isActive) {
        logs.forEach { log ->
            if ( log != null ) {
                Log.i(logStrTag, log.toString())
            } else {
                Log.i(logStrTag, ".: === LOG_IS_NULL === :.")
            }
        }
    }
}

fun dLog(vararg logs: Any?, isActive: Boolean = true) {
    if (isActive) {
        logs.forEach { log ->
            if ( log != null ) {
                Log.d(logStrTag, log.toString())
            } else {
                Log.d(logStrTag, ".: === LOG_IS_NULL === :.")
            }
        }
    }
}

fun wLog(vararg logs: Any?, isActive: Boolean = true) {
    if (isActive) {
        logs.forEach { log ->
            if ( log != null ) {
                Log.w(logStrTag, log.toString())
            } else {
                Log.w(logStrTag, ".: === LOG_IS_NULL === :.")
            }
        }
    }
}

fun eLog(vararg logs: Any?, isActive: Boolean = true) {
    if (isActive) {
        logs.forEach { log ->
            if ( log != null ) {
                Log.e(logStrTag, log.toString())
            } else {
                Log.e(logStrTag, ".: === LOG_IS_NULL === :.")
            }
        }
    }
}
