@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package ir.farsroidx.core.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import ir.farsroidx.core.R
import ir.farsroidx.core.additives.gone
import ir.farsroidx.core.additives.px
import ir.farsroidx.core.additives.visible
import ir.farsroidx.core.additives.visibleOrGone
import ir.farsroidx.core.databinding.LayoutNumberButtonBinding

internal class NumberButton : ConstraintLayout {

    private lateinit var dataBinding: LayoutNumberButtonBinding

    enum class NumberId(val id: Int) {
        NUM_ID_1(1), NUM_ID_2(2), NUM_ID_3(3),
        NUM_ID_4(4), NUM_ID_5(5), NUM_ID_6(6),
        NUM_ID_7(7), NUM_ID_8(8), NUM_ID_9(9),
        NUM_ID_0(0), IMAGE(-2)
    }

    private val numPadData = mapOf(
        NumberId.IMAGE.id     to "",
        NumberId.NUM_ID_0.id  to "+",
        NumberId.NUM_ID_1.id  to "",
        NumberId.NUM_ID_2.id  to "ABC",
        NumberId.NUM_ID_3.id  to "DEF",
        NumberId.NUM_ID_4.id  to "GHI",
        NumberId.NUM_ID_5.id  to "JKL",
        NumberId.NUM_ID_6.id  to "MNO",
        NumberId.NUM_ID_7.id  to "PQRS",
        NumberId.NUM_ID_8.id  to "TUV",
        NumberId.NUM_ID_9.id  to "WXYZ",
    )

    private val gradientDrawable = GradientDrawable()

    private var numberId    : Int = NumberId.NUM_ID_7.id
    private var iconResId   : Int = 0
    private var iconTintRes : Int = 0

    constructor(context: Context)
        : this(context, null)

    constructor(context: Context, attrs: AttributeSet?)
        : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
        : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
        : super(context, attrs, defStyleAttr, defStyleRes)
    {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {

        dataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.layout_number_button, this, true
        )

        attrs?.let {
            initAttributes(it, defStyleAttr, defStyleRes)
        }
    }

    private fun initAttributes(attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) {

        context.obtainStyledAttributes(
            attrs, R.styleable.NumberButton, defStyleAttr, defStyleRes
        ).apply {

            getBoolean(
                R.styleable.NumberButton_frsx_ShowButtonText, true
            ).apply {
                setShowButtonText(this)
            }

            getColor(
                R.styleable.NumberButton_frsx_ButtonTextColor, Color.WHITE
            ).apply {
                setButtonTextColor(this)
            }

            getColor(
                R.styleable.NumberButton_frsx_ButtonNumberColor, Color.WHITE
            ).apply {
                setButtonNumberColor(this)
            }

            iconResId = getResourceId(
                R.styleable.NumberButton_frsx_ButtonIcon,
                R.drawable.icon_backspace
            )

            iconTintRes = getColor(
                R.styleable.NumberButton_frsx_ButtonIconTint,
                Color.WHITE
            )

            val backgroundColor = getColor(
                R.styleable.NumberButton_frsx_ButtonBackgroundColor, Color.BLACK
            )

            val rippleColor = getColor(
                R.styleable.NumberButton_frsx_ButtonRippleColor, Color.BLUE
            )

            val radius = getDimension(
                R.styleable.NumberButton_frsx_ButtonRadius, 6.px.toFloat()
            )

            var maskResId: Int? = getResourceId(
                R.styleable.NumberButton_frsx_ButtonRippleMaskDrawable, -101
            )

            if (maskResId == -101) maskResId = null

            getInt(R.styleable.NumberButton_frsx_Number, 0).also {
                setNumberById(it)
            }

            setButtonDetails( backgroundColor, rippleColor, radius, maskResId )

            recycle()
        }
    }

    fun setNumber(numberId: NumberId) {
        setNumberById(numberId.id)
    }

    private fun setNumberById(id: Int) {

        numberId = id

        dataBinding.apply {

            if (id == NumberId.IMAGE.id) {

                imgView.visible()
                txtNumPad.gone()
                txtNumValues.gone()

                setIconResource(iconResId)

                setIconTint(iconTintRes)

            } else {

                imgView.gone()
                txtNumPad.visible()
                txtNumValues.visible()

                txtNumPad.text = id.toString()
                txtNumValues.text = numPadData[id]
            }
        }
    }

    fun setIconResource(@DrawableRes resId: Int) {

        iconResId = resId

        if (numberId != NumberId.IMAGE.id) return

        dataBinding.imgView.setImageResource(iconResId)
    }

    fun setIconTint(@ColorRes resId: Int) {

        iconTintRes = resId

        if (numberId != NumberId.IMAGE.id) return

        dataBinding.imgView.setColorFilter(iconTintRes, PorterDuff.Mode.SRC_IN)
    }

    fun setShowButtonText(isShown: Boolean) {
        dataBinding.txtNumValues.visibleOrGone(isShown)
    }

    fun setButtonTextColor(color: Int) {
        dataBinding.txtNumValues.setTextColor(color)
    }

    fun setButtonNumberColor(color: Int) {
        dataBinding.txtNumPad.setTextColor(color)
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        dataBinding.button.setOnClickListener(listener)
    }

    fun setOnClickListener(listener: (View) -> Unit) {
        dataBinding.button.setOnClickListener {
            listener(it)
        }
    }

    override fun setOnLongClickListener(listener: OnLongClickListener?) {
        dataBinding.button.setOnLongClickListener(listener)
    }

    fun setOnLongClickListener(listener: (View) -> Boolean) {
        dataBinding.button.setOnLongClickListener {
            listener(it)
        }
    }

    fun setButtonDetails(
        backgroundColor: Int,
        rippleColor: Int = Color.DKGRAY,
        radius: Float = 6.px.toFloat(),
        @DrawableRes maskResId: Int? = null
    ) {
        dataBinding.apply {
            button.background = getRippleDrawable(
                backgroundColor, rippleColor, radius, maskResId
            )
        }
    }

    private fun getRippleDrawable(
        backgroundColor: Int,
        rippleColor: Int = Color.DKGRAY,
        radius: Float = 6.px.toFloat(),
        @DrawableRes maskResId: Int? = null
    ): RippleDrawable {
        return RippleDrawable(
            getPressedColorStateList(backgroundColor, rippleColor),
            getGradientDrawable(backgroundColor, rippleColor, radius),
            if (maskResId != null) {
                ContextCompat.getDrawable(context, maskResId)
            } else null
        )
    }

    private fun getPressedColorStateList(
        backgroundColor: Int, rippleColor: Int
    ): ColorStateList {
        return ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf(android.R.attr.state_focused),
                intArrayOf(android.R.attr.state_activated),
                intArrayOf()
            ), intArrayOf(
                rippleColor,
                rippleColor,
                rippleColor,
                backgroundColor
            )
        )
    }

    private fun getGradientDrawable(
        backgroundColor: Int, rippleColor: Int, radius: Float
    ): GradientDrawable {
        return gradientDrawable.apply {
            setColor(backgroundColor)
            setStroke(0, rippleColor)
            cornerRadius = radius
            elevation = 12.px.toFloat()
        }
    }
}