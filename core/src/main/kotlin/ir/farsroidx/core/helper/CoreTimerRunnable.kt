@file:Suppress("unused")

package ir.farsroidx.core.helper

import android.os.Handler
import android.os.Looper

class CoreTimerRunnable(private val initValue: Int, val run: (String, Boolean) -> Unit) : Runnable {

    private val handler: Handler = Handler(Looper.getMainLooper())

    private var time = 0

    init {
        time = initValue
    }

    override fun run() {

        val formatted = "${
            (time / 60).toString().padStart(2, '0')
        } : ${
            (time % 60).toString().padStart(2, '0')
        }"

        // 01:21

        if (time > 0) {
            run( formatted , false )
            handler.postDelayed(this, 1_000)
        } else {
            run( formatted , true )
        }

        time -= 1
    }

    fun reset() {
        time = initValue
    }

    fun stop() {
        handler.removeCallbacks(this)
    }
}