@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package ir.farsroidx.core.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import ir.farsroidx.core.R
import ir.farsroidx.core.additives.px
import ir.farsroidx.core.additives.visibleOrGone
import ir.farsroidx.core.additives.visibleOrInvisible
import ir.farsroidx.core.databinding.LayoutNumberPadBinding

class NumberPad : ConstraintLayout {

    private lateinit var dataBinding: LayoutNumberPadBinding

    private var listener: OnNumPadClickListener? = null
    
    private var doneIconId    : Int = 0
    private var doneIconTint  : Int = 0
    private var deleteIconId  : Int = 0
    private var deleteIconTint: Int = 0

    enum class NumPad {
        NUM_PAD_1, NUM_PAD_2, NUM_PAD_3,
        NUM_PAD_4, NUM_PAD_5, NUM_PAD_6,
        NUM_PAD_7, NUM_PAD_8, NUM_PAD_9,
        DONE     , NUM_PAD_0, BACKSPACE
    }

    constructor(context: Context)
        : this(context, null)

    constructor(context: Context, attrs: AttributeSet?)
        : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
        : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
        : super(context, attrs, defStyleAttr, defStyleRes) {
        initialize(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun initialize(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {

        dataBinding = DataBindingUtil.inflate<LayoutNumberPadBinding>(
            LayoutInflater.from(context), R.layout.layout_number_pad, this, true
        ).apply {

            numPadBackspace.setOnClickListener { onClicked(NumPad.BACKSPACE) }
            numPadDone.setOnClickListener { onClicked(NumPad.DONE) }
            numPad0.setOnClickListener { onClicked(NumPad.NUM_PAD_0) }
            numPad1.setOnClickListener { onClicked(NumPad.NUM_PAD_1) }
            numPad2.setOnClickListener { onClicked(NumPad.NUM_PAD_2) }
            numPad3.setOnClickListener { onClicked(NumPad.NUM_PAD_3) }
            numPad4.setOnClickListener { onClicked(NumPad.NUM_PAD_4) }
            numPad5.setOnClickListener { onClicked(NumPad.NUM_PAD_5) }
            numPad6.setOnClickListener { onClicked(NumPad.NUM_PAD_6) }
            numPad7.setOnClickListener { onClicked(NumPad.NUM_PAD_7) }
            numPad8.setOnClickListener { onClicked(NumPad.NUM_PAD_8) }
            numPad9.setOnClickListener { onClicked(NumPad.NUM_PAD_9) }

            numPadBackspace.setOnLongClickListener { onLongClicked(NumPad.BACKSPACE) }
            numPadDone.setOnLongClickListener { onLongClicked(NumPad.DONE) }
            numPad0.setOnLongClickListener { onLongClicked(NumPad.NUM_PAD_0) }
            numPad1.setOnLongClickListener { onLongClicked(NumPad.NUM_PAD_1) }
            numPad2.setOnLongClickListener { onLongClicked(NumPad.NUM_PAD_2) }
            numPad3.setOnLongClickListener { onLongClicked(NumPad.NUM_PAD_3) }
            numPad4.setOnLongClickListener { onLongClicked(NumPad.NUM_PAD_4) }
            numPad5.setOnLongClickListener { onLongClicked(NumPad.NUM_PAD_5) }
            numPad6.setOnLongClickListener { onLongClicked(NumPad.NUM_PAD_6) }
            numPad7.setOnLongClickListener { onLongClicked(NumPad.NUM_PAD_7) }
            numPad8.setOnLongClickListener { onLongClicked(NumPad.NUM_PAD_8) }
            numPad9.setOnLongClickListener { onLongClicked(NumPad.NUM_PAD_9) }

        }

        attrs?.let {
            context.obtainStyledAttributes(
                it, R.styleable.NumberPad, defStyleAttr, defStyleRes
            ).apply {

                dataBinding.apply {

                    getBoolean(
                        R.styleable.NumberPad_frsx_ShowButtonText, true
                    ).apply {
                        setShowButtonText(this)
                    }

                    getBoolean(
                        R.styleable.NumberPad_frsx_ShowDoneButton, true
                    ).apply {
                        setShowDoneButton(this)
                    }

                    getColor(
                        R.styleable.NumberPad_frsx_ButtonTextColor, Color.WHITE
                    ).apply {
                        setButtonTextColor(this)
                    }

                    getColor(
                        R.styleable.NumberPad_frsx_ButtonNumberColor, Color.WHITE
                    ).apply {
                        setButtonNumberColor(this)
                    }

                    getResourceId(
                        R.styleable.NumberPad_frsx_ButtonDoneIcon,
                        R.drawable.icon_done
                    ).apply {
                        numPadDone.setIconResource(this)
                    }

                    getColor(
                        R.styleable.NumberPad_frsx_ButtonDoneIconTint,
                        Color.WHITE
                    ).apply {
                        numPadDone.setIconTint(this)
                    }

                    getResourceId(
                        R.styleable.NumberPad_frsx_ButtonBackspaceIcon,
                        R.drawable.icon_backspace
                    ).apply {
                        numPadBackspace.setIconResource(this)
                    }

                    getColor(
                        R.styleable.NumberPad_frsx_ButtonBackspaceIconTint,
                        Color.WHITE
                    ).apply {
                        numPadBackspace.setIconTint(this)
                    }

                    getColor(
                        R.styleable.NumberPad_frsx_OverlayOnDisableColor,
                        Color.WHITE
                    ).apply {
                        overlay.setBackgroundColor(this)
                    }

                    getFloat(
                        R.styleable.NumberPad_frsx_OverlayOnDisableAlpha,
                        0.5F
                    ).apply {
                        overlay.alpha = this
                    }

                    getBoolean(
                        R.styleable.NumberPad_frsx_IsEnabled,
                        true
                    ).apply {
                        setEnableNumPad(this)
                    }

                    // TODO: DEFAULT ===========================================================

                    val backgroundColor = getColor(
                        R.styleable.NumberPad_frsx_ButtonBackgroundColor, Color.BLACK
                    )

                    val rippleColor = getColor(
                        R.styleable.NumberPad_frsx_ButtonRippleColor, Color.BLUE
                    )

                    val radius = getDimension(
                        R.styleable.NumberPad_frsx_ButtonRadius, 6.px.toFloat()
                    )

                    var maskResId: Int? = getResourceId(
                        R.styleable.NumberPad_frsx_ButtonRippleMaskDrawable, -101
                    )

                    if (maskResId == -101) maskResId = null

                    setButtonsDetails(
                        backgroundColor, rippleColor, radius, maskResId
                    )

                    // TODO: DONE ==============================================================

                    val doneBackgroundColor = getColor(
                        R.styleable.NumberPad_frsx_ButtonDoneBackgroundColor, backgroundColor
                    )

                    val doneRippleColor = getColor(
                        R.styleable.NumberPad_frsx_ButtonDoneRippleColor, rippleColor
                    )

                    val doneRadius = getDimension(
                        R.styleable.NumberPad_frsx_ButtonDoneRadius, radius
                    )

                    var doneMaskResId: Int? = getResourceId(
                        R.styleable.NumberPad_frsx_ButtonDoneRippleMaskDrawable,
                        maskResId ?: -101
                    )

                    if (doneMaskResId == -101) doneMaskResId = null

                    numPadDone.setButtonDetails(
                        doneBackgroundColor, doneRippleColor, doneRadius, doneMaskResId
                    )

                    // TODO: Backspace =========================================================

                    val backspaceBackgroundColor = getColor(
                        R.styleable.NumberPad_frsx_ButtonBackspaceBackgroundColor, backgroundColor
                    )

                    val backspaceRippleColor = getColor(
                        R.styleable.NumberPad_frsx_ButtonBackspaceRippleColor, rippleColor
                    )

                    val backspaceRadius = getDimension(
                        R.styleable.NumberPad_frsx_ButtonBackspaceRadius, radius
                    )

                    var backspaceMaskResId: Int? = getResourceId(
                        R.styleable.NumberPad_frsx_ButtonBackspaceRippleMaskDrawable,
                        maskResId ?: -101
                    )

                    if (backspaceMaskResId == -101) backspaceMaskResId = null

                    numPadBackspace.setButtonDetails(
                        backspaceBackgroundColor, backspaceRippleColor, backspaceRadius, backspaceMaskResId
                    )
                }

                recycle()
            }
        }
    }
    
    private fun LayoutNumberPadBinding.setButtonsDetails(
        backgroundColor: Int,
        rippleColor: Int = Color.DKGRAY,
        radius: Float = 6.px.toFloat(),
        @DrawableRes maskResId: Int? = null
    ) {
        numPad0.setButtonDetails(backgroundColor, rippleColor, radius, maskResId)
        numPad1.setButtonDetails(backgroundColor, rippleColor, radius, maskResId)
        numPad2.setButtonDetails(backgroundColor, rippleColor, radius, maskResId)
        numPad3.setButtonDetails(backgroundColor, rippleColor, radius, maskResId)
        numPad4.setButtonDetails(backgroundColor, rippleColor, radius, maskResId)
        numPad5.setButtonDetails(backgroundColor, rippleColor, radius, maskResId)
        numPad6.setButtonDetails(backgroundColor, rippleColor, radius, maskResId)
        numPad7.setButtonDetails(backgroundColor, rippleColor, radius, maskResId)
        numPad8.setButtonDetails(backgroundColor, rippleColor, radius, maskResId)
        numPad9.setButtonDetails(backgroundColor, rippleColor, radius, maskResId)
    }

    fun setOnNumPadClickListener(listener: OnNumPadClickListener?) {
        this.listener = listener
    }

    fun setOnNumPadClickListener(listener: (NumPad) -> Unit) {
        this.listener = object : AbstractOnNumPadClickListener() {
            override fun onClick(numPad: NumPad) {
                listener(numPad)
            }
        }
    }

    fun setOnNumPadLongClickListener(listener: (NumPad) -> Unit) {
        this.listener = object : AbstractOnNumPadClickListener() {
            override fun onLongClick(numPad: NumPad) {
                listener(numPad)
            }
        }
    }

    fun setEnableNumPad(isEnable: Boolean) {
        dataBinding.overlay.visibleOrGone(!isEnable)
    }

    fun setShowButtonText(isShown: Boolean) {
        dataBinding.apply {
            numPad0.setShowButtonText(isShown)
            numPad1.setShowButtonText(isShown)
            numPad2.setShowButtonText(isShown)
            numPad3.setShowButtonText(isShown)
            numPad4.setShowButtonText(isShown)
            numPad5.setShowButtonText(isShown)
            numPad6.setShowButtonText(isShown)
            numPad7.setShowButtonText(isShown)
            numPad8.setShowButtonText(isShown)
            numPad9.setShowButtonText(isShown)
        }
    }

    fun setButtonTextColor(@ColorRes color: Int) {
        dataBinding.apply {
            numPad0.setButtonTextColor(color)
            numPad1.setButtonTextColor(color)
            numPad2.setButtonTextColor(color)
            numPad3.setButtonTextColor(color)
            numPad4.setButtonTextColor(color)
            numPad5.setButtonTextColor(color)
            numPad6.setButtonTextColor(color)
            numPad7.setButtonTextColor(color)
            numPad8.setButtonTextColor(color)
            numPad9.setButtonTextColor(color)
        }
    }

    fun setButtonNumberColor(@ColorRes color: Int) {
        dataBinding.apply {
            numPad0.setButtonNumberColor(color)
            numPad1.setButtonNumberColor(color)
            numPad2.setButtonNumberColor(color)
            numPad3.setButtonNumberColor(color)
            numPad4.setButtonNumberColor(color)
            numPad5.setButtonNumberColor(color)
            numPad6.setButtonNumberColor(color)
            numPad7.setButtonNumberColor(color)
            numPad8.setButtonNumberColor(color)
            numPad9.setButtonNumberColor(color)
        }
    }

    fun setShowDoneButton(isShown: Boolean) {
        dataBinding.numPadDone.visibleOrInvisible(isShown)
    }

    private fun onClicked(numPad: NumPad) {
        listener?.onClick(numPad)
    }

    private fun onLongClicked(numPad: NumPad): Boolean {
        listener?.onLongClick(numPad)
        return true
    }

    interface OnNumPadClickListener {
        fun onClick(numPad: NumPad)
        fun onLongClick(numPad: NumPad)
    }

    abstract class AbstractOnNumPadClickListener: OnNumPadClickListener {
        override fun onClick(numPad: NumPad) {}
        override fun onLongClick(numPad: NumPad) {}
    }
}