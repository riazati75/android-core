@file:Suppress("unused")

package ir.farsroidx.core.additives

import android.util.Log

// TODO: Log =============================================================================== Log ===

private const val logStrTag = "CentralCore"

fun vLog(vararg logs: Any?) {
    logs.forEach { log ->
        if ( log != null ) {
            Log.v(logStrTag, log.toString())
        }
    }
}

fun iLog(vararg logs: Any?) {
    logs.forEach { log ->
        if ( log != null ) {
            Log.i(logStrTag, log.toString())
        }
    }
}

fun dLog(vararg logs: Any?) {
    logs.forEach { log ->
        if ( log != null ) {
            Log.d(logStrTag, log.toString())
        }
    }
}

fun wLog(vararg logs: Any?) {
    logs.forEach { log ->
        if ( log != null ) {
            Log.w(logStrTag, log.toString())
        }
    }
}

fun eLog(vararg logs: Any?) {
    logs.forEach { log ->
        if ( log != null ) {
            Log.e(logStrTag, log.toString())
        }
    }
}
