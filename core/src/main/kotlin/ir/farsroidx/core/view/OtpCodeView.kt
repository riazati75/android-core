package ir.farsroidx.core.view

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import ir.farsroidx.core.R
import ir.farsroidx.core.additives.px

class OtpCodeView : LinearLayout, View.OnKeyListener {

    private var codeValue    : String = ""
    private var codeLength   : Int    = 4
    private var marginBetween: Float  = 16F.px

    private var loggedBackground: Int   = 0
    private var loggedWHeight   : Float = 45F.px
    private var loggedElevation : Float = 3F
    private var loggedTextColor : Int   = Color.BLUE
    private var loggedTextSize  : Float = 16F
    private var loggedTextStyle : Int   = 0

    private var defaultBackground: Int   = 0
    private var defaultWHeight   : Float = 45F.px
    private var defaultElevation : Float = 3F
    private var defaultTextColor : Int   = Color.GRAY
    private var defaultTextSize  : Float = 16F
    private var defaultTextStyle : Int   = 0

    private val codeViews = mutableListOf<OtpTextView>()

    private var onValueChange : ((String, Boolean) -> Unit)? = null

    private lateinit var inputMethodManager: InputMethodManager

    constructor(context: Context)
        : this(context, null)

    constructor(context: Context, attrs: AttributeSet?)
        : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
        : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
        : super(context, attrs, defStyleAttr, defStyleRes) {
            initializeView(context, attrs, defStyleAttr, defStyleRes)
        }

    private fun initializeView(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
    ) {

        inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager

        attrs?.let { initializeAttribute(context, it, defStyleAttr, defStyleRes) }

        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
        )

        isFocusableInTouchMode = true
        isFocusable            = true
        isClickable            = true
        clipChildren           = false
        clipToPadding          = false
        clipToOutline          = false
        gravity                = Gravity.CENTER
        orientation            = HORIZONTAL

        setOnKeyListener(this)

        setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                toggleKeyboard()
            }
        }

        setOnClickListener { toggleKeyboard() }

        if (isInEditMode) { initViews( codeLength ) }

        toggleKeyboard()
    }

    @Suppress("DEPRECATION")
    private fun toggleKeyboard() {
//        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0)
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        requestFocus()
    }

    private fun initializeAttribute(
        context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int
    ) {
        context.obtainStyledAttributes(
            attrs, R.styleable.OtpCodeView, defStyleAttr, defStyleRes
        ).apply {

            // Todo: Base ==========================================================================
            getInt(R.styleable.OtpCodeView_frsx_CodeLength, codeLength).also {
                codeLength = it
            }

            getDimension(R.styleable.OtpCodeView_frsx_MarginBetween, marginBetween).also {
                marginBetween = it
            }

            // Todo: Logged ========================================================================
            getResourceId(R.styleable.OtpCodeView_frsx_LoggedBackground, loggedBackground).also {
                loggedBackground = it
            }

            getDimension(R.styleable.OtpCodeView_frsx_LoggedWHeight, loggedWHeight).also {
                loggedWHeight = it
            }

            getDimension(R.styleable.OtpCodeView_frsx_LoggedElevation, loggedElevation).also {
                loggedElevation = it
            }

            getColor(R.styleable.OtpCodeView_frsx_LoggedTextColor, loggedTextColor).also {
                loggedTextColor = it
            }

            getFloat(R.styleable.OtpCodeView_frsx_LoggedTextSize, loggedTextSize).also {
                loggedTextSize = it
            }

            getInt(R.styleable.OtpCodeView_frsx_LoggedTextStyle, loggedTextStyle).also {
                loggedTextStyle = it
            }

            // Todo: Default =======================================================================
            getResourceId(R.styleable.OtpCodeView_frsx_DefaultBackground, defaultBackground).also {
                defaultBackground = it
            }

            getDimension(R.styleable.OtpCodeView_frsx_DefaultWHeight, defaultWHeight).also {
                defaultWHeight = it
            }

            getDimension(R.styleable.OtpCodeView_frsx_DefaultElevation, defaultElevation).also {
                defaultElevation = it
            }

            getColor(R.styleable.OtpCodeView_frsx_DefaultTextColor, defaultTextColor).also {
                defaultTextColor = it
            }

            getFloat(R.styleable.OtpCodeView_frsx_DefaultTextSize, defaultTextSize).also {
                defaultTextSize = it
            }

            getInt(R.styleable.OtpCodeView_frsx_DefaultTextStyle, defaultTextStyle).also {
                defaultTextStyle = it
            }

            recycle()
        }
    }

    private fun initViews(codeLength: Int) {

        removeAllViews()

        codeViews.clear()

        for (i in 0 until codeLength) {

            OtpTextView(context).apply {

                val viewMarginStart = if (i == 0) { 0 } else { marginBetween / 2 }
                val viewMarginEnd = if (i == (codeLength - 1)) { 0 } else { marginBetween / 2 }

                toDefaultView(viewMarginStart.toInt(),viewMarginEnd.toInt())

                gravity = Gravity.CENTER

                text = if (isInEditMode) {
                    "5"
                } else {
                    ""
                }

                textAlignment = TEXT_ALIGNMENT_CENTER

                this@OtpCodeView.addView(this)

                codeViews.add(this)
            }
        }
    }

    fun initCodeLength(codeLength: Int, onValueChange: (String, Boolean) -> Unit = { _, _ -> }) {
        this.codeLength    = codeLength
        this.onValueChange = onValueChange
        initViews(codeLength)
    }

    private fun clearLastValue() {

        if (codeValue.isNotEmpty()) {

            codeViews[codeValue.length - 1].apply {
                text = ""
                toDefaultView()
            }

            codeValue = codeValue.substring(0, codeValue.length - 1)

            onValueChange?.let { it(codeValue, false) }
        }
    }

    private fun addValue(value: Int) {

        if (codeValue.length < codeLength) {

            codeViews[codeValue.length].apply {
                text = value.toString()
                toLoggedView()
            }

            codeValue += value

            onValueChange?.let { it( codeValue, (codeValue.length == codeLength) ) }
        }
    }

    private class OtpTextView(context: Context) : AppCompatTextView(context) {
        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            super.onMeasure(heightMeasureSpec, heightMeasureSpec)
        }
    }

    private fun OtpTextView.toLoggedView(viewMarginStart: Int? = null, viewMarginEnd: Int? = null) {

        if (viewMarginStart != null && viewMarginEnd != null) {
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, loggedWHeight.toInt()).apply {
                marginEnd   = viewMarginEnd
                marginStart = viewMarginStart
            }
        }

        if (loggedBackground != 0) {
            setBackgroundResource(loggedBackground)
        } else {
            setBackgroundColor(Color.WHITE)
        }

        setTextSize(TypedValue.COMPLEX_UNIT_SP, loggedTextSize)

        setTextColor(loggedTextColor)

        if (loggedTextStyle != 0) {
            when (loggedTextStyle) {
                1 -> {
                    setTypeface(typeface, Typeface.BOLD)
                }
                2 -> {
                    setTypeface(typeface, Typeface.ITALIC)
                }
                3 -> {
                    setTypeface(typeface, Typeface.BOLD_ITALIC)
                }
            }
        }

        elevation = loggedElevation.px
    }

    private fun OtpTextView.toDefaultView(viewMarginStart: Int? = null, viewMarginEnd: Int? = null) {

        if (viewMarginStart != null && viewMarginEnd != null) {
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, defaultWHeight.toInt()).apply {
                marginEnd   = viewMarginEnd
                marginStart = viewMarginStart
            }
        }

        if (defaultBackground != 0) {
            setBackgroundResource(defaultBackground)
        } else {
            setBackgroundColor(Color.LTGRAY)
        }

        setTextSize(TypedValue.COMPLEX_UNIT_SP, defaultTextSize)

        setTextColor(defaultTextColor)

        if (defaultTextStyle != 0) {
            when (defaultTextStyle) {
                1 -> {
                    setTypeface(typeface, Typeface.BOLD)
                }

                2 -> {
                    setTypeface(typeface, Typeface.ITALIC)
                }

                3 -> {
                    setTypeface(typeface, Typeface.BOLD_ITALIC)
                }
            }
        }

        elevation = defaultElevation.px
    }

    override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            when(keyCode) {
                KeyEvent.KEYCODE_DEL -> {
                    clearLastValue()
                }
                KeyEvent.KEYCODE_0 -> {
                    addValue(0)
                }
                KeyEvent.KEYCODE_1 -> {
                    addValue(1)
                }
                KeyEvent.KEYCODE_2 -> {
                    addValue(2)
                }
                KeyEvent.KEYCODE_3 -> {
                    addValue(3)
                }
                KeyEvent.KEYCODE_4 -> {
                    addValue(4)
                }
                KeyEvent.KEYCODE_5 -> {
                    addValue(5)
                }
                KeyEvent.KEYCODE_6 -> {
                    addValue(6)
                }
                KeyEvent.KEYCODE_7 -> {
                    addValue(7)
                }
                KeyEvent.KEYCODE_8 -> {
                    addValue(8)
                }
                KeyEvent.KEYCODE_9 -> {
                    addValue(9)
                }
                else -> return false
            }
            return true
        }
        return false
    }
}