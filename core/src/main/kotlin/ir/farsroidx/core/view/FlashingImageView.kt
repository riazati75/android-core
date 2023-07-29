@file:Suppress("MemberVisibilityCanBePrivate")

package ir.farsroidx.core.view

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import ir.farsroidx.core.R

class FlashingImageView : AppCompatImageView {

    private var defaultColor    = 0
    private var firstColor      = 0
    private var secondColor     = 0
    private var timeChangeColor = 0
    private var startAutoChange = false
    private var stateColor      = false

    private var handler     = Handler(Looper.getMainLooper())
    private var autoChanger = AutoChanger()

    constructor(context: Context)
        : this(context, null)

    constructor(context: Context, attrs: AttributeSet?)
        : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
        : super(context, attrs, defStyleAttr) {
            initialize(context, attrs, defStyleAttr)
        }

    private fun initialize(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {

        attrs?.let {

            context.theme.obtainStyledAttributes(
                it, R.styleable.FlashingImageView, defStyleAttr, 0
            ).apply {

                defaultColor    = getColor(R.styleable.FlashingImageView_frsx_DefaultColor, 0)
                firstColor      = getColor(R.styleable.FlashingImageView_frsx_FirstColor, 0)
                secondColor     = getColor(R.styleable.FlashingImageView_frsx_SecondColor, 0)
                timeChangeColor = getColor(R.styleable.FlashingImageView_frsx_TimeChangeColor, 0)
                startAutoChange = getBoolean(R.styleable.FlashingImageView_frsx_StartAutoChange, false)

                recycle()
            }
        }

        initFirstCalled(this)

        if (startAutoChange) {
            startFlashing()
        }
    }

    private fun initFirstCalled(flashingImageView: FlashingImageView) {
        flashingImageView.setColorFilter(defaultColor)
    }

    fun setAutoChange(auto: Boolean) {
        startAutoChange = auto
    }

    fun startFlashing() {
        if (startAutoChange) {
            handler.post(autoChanger)
        }
    }

    fun stopFlashing() {
        handler.removeCallbacks(autoChanger)
        initFirstCalled(this)
    }

    private inner class AutoChanger : Runnable {
        override fun run() {
            stateColor = if (stateColor) {
                this@FlashingImageView.setColorFilter(secondColor)
                false
            } else {
                this@FlashingImageView.setColorFilter(firstColor)
                true
            }
            handler.postDelayed(this, timeChangeColor.toLong())
        }
    }
}
