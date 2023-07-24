@file:Suppress("unused")

package ir.farsroidx.core.additives

import android.util.Log

// TODO: Log =============================================================================== Log ===

private const val logStrTag = "CentralCore"

fun vLog(log: Any?) {
    if ( log != null ) {
        Log.v(logStrTag, log.toString())
    }
}

fun iLog(log: Any?) {
    if ( log != null ) {
        Log.i(logStrTag, log.toString())
    }
}

fun dLog(log: Any?) {
    if ( log != null ) {
        Log.d(logStrTag, log.toString())
    }
}

fun wLog(log: Any?) {
    if ( log != null ) {
        Log.w(logStrTag, log.toString())
    }
}

fun eLog(log: Any?) {
    if ( log != null ) {
        Log.e(logStrTag, log.toString())
    }
}
