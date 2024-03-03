package ir.farsroidx.core.view

import android.content.Context
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import ir.farsroidx.core.R

class SquareCoordinatorLayout : CoordinatorLayout {

    companion object {
        private const val SQUARED_BY_WIDTH  = 14
        private const val SQUARED_BY_HEIGHT = 15
    }

    private var mSquaredBy = SQUARED_BY_WIDTH

    constructor(context: Context)
        : this(context, null)

    constructor(context: Context, attrs: AttributeSet?)
        : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
        : super(context, attrs, defStyleAttr) {
            initialize(context, attrs, defStyleAttr)
        }

    private fun initialize(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int
    ) {
        attrs?.let {
            context.obtainStyledAttributes(
                it, R.styleable.SquareCoordinatorLayout, defStyleAttr, 0
            ).apply {
                mSquaredBy = getInt(R.styleable.SquareCoordinatorLayout_frsx_SquaredBy, SQUARED_BY_WIDTH)
                recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        when(mSquaredBy) {
            SQUARED_BY_WIDTH  -> super.onMeasure(widthMeasureSpec, widthMeasureSpec)
            SQUARED_BY_HEIGHT -> super.onMeasure(heightMeasureSpec, heightMeasureSpec)
            else -> throw IllegalArgumentException("type of enumeration not supported.")
        }
    }
}