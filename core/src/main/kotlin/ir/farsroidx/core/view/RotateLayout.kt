@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION", "unused", "UNUSED_PARAMETER")

package ir.farsroidx.core.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import ir.farsroidx.core.R
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.sin

class RotateLayout : ViewGroup {

    private var angle           = 0
    private val rotateMatrix    = Matrix()
    private val viewRectRotated = Rect()
    private val tempRectF1      = RectF()
    private val tempRectF2      = RectF()
    private val viewTouchPoint  = FloatArray(2)
    private val childTouchPoint = FloatArray(2)
    private var angleChanged    = true

    private val view: View?
        get() = if (childCount > 0) {
            getChildAt(0)
        } else {
            null
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

    private fun initialize(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
    ) {

        context.obtainStyledAttributes(attrs, R.styleable.RotateLayout).apply {
            angle = getInt(R.styleable.RotateLayout_frsx_Angle, angle)
            recycle()
        }

        setWillNotDraw(false)
    }

    fun getAngle() = angle

    fun setAngle(angle: Int) {
        if (this.angle != angle) {
            this.angle = angle
            angleChanged = true
            requestLayout()
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val child = view
        if (child != null) {
            if (abs(angle % 180) == 90) {
                measureChild(child, heightMeasureSpec, widthMeasureSpec)
                setMeasuredDimension(
                    resolveSize(child.measuredHeight, widthMeasureSpec),
                    resolveSize(child.measuredWidth, heightMeasureSpec)
                )
            } else if (abs(angle % 180) == 0) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec)
                setMeasuredDimension(
                    resolveSize(child.measuredWidth, widthMeasureSpec),
                    resolveSize(child.measuredHeight, heightMeasureSpec)
                )
            } else {
                val childWithMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                val childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                measureChild(child, childWithMeasureSpec, childHeightMeasureSpec)
                val measuredWidth = ceil(
                    child.measuredWidth * abs(
                        cos(
                            angleC()
                        )
                    ) + child.measuredHeight * abs(
                        sin(
                            angleC()
                        )
                    )
                ).toInt()
                val measuredHeight = ceil(
                    child.measuredWidth * abs(
                        sin(
                            angleC()
                        )
                    ) + child.measuredHeight * abs(
                        cos(
                            angleC()
                        )
                    )
                ).toInt()
                setMeasuredDimension(
                    resolveSize(measuredWidth, widthMeasureSpec),
                    resolveSize(measuredHeight, heightMeasureSpec)
                )
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val layoutWidth = r - l
        val layoutHeight = b - t
        if (angleChanged || changed) {
            val layoutRect = tempRectF1
            layoutRect[0f, 0f, layoutWidth.toFloat()] = layoutHeight.toFloat()
            val layoutRectRotated = tempRectF2
            rotateMatrix.setRotate(angle.toFloat(), layoutRect.centerX(), layoutRect.centerY())
            rotateMatrix.mapRect(layoutRectRotated, layoutRect)
            layoutRectRotated.round(viewRectRotated)
            angleChanged = false
        }
        val child = view
        if (child != null) {
            val childLeft = (layoutWidth - child.measuredWidth) / 2
            val childTop = (layoutHeight - child.measuredHeight) / 2
            val childRight = childLeft + child.measuredWidth
            val childBottom = childTop + child.measuredHeight
            child.layout(childLeft, childTop, childRight, childBottom)
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        canvas.save()
        canvas.rotate(-angle.toFloat(), width / 2f, height / 2f)
        super.dispatchDraw(canvas)
        canvas.restore()
    }

    override fun invalidateChildInParent(location: IntArray, dirty: Rect): ViewParent {
        invalidate()
        return super.invalidateChildInParent(location, dirty)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        viewTouchPoint[0] = event.x
        viewTouchPoint[1] = event.y
        rotateMatrix.mapPoints(childTouchPoint, viewTouchPoint)
        event.setLocation(childTouchPoint[0], childTouchPoint[1])
        val result = super.dispatchTouchEvent(event)
        event.setLocation(viewTouchPoint[0], viewTouchPoint[1])
        return result
    }

    private fun angleC(): Double {
        return (2 * Math.PI) * angle / 360
    }
}