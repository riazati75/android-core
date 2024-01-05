@file:Suppress("unused")

package ir.farsroidx.core.additives

import android.app.Activity
import android.graphics.Color
import android.graphics.Point
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

// TODO: View ============================================================================= View ===

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.visibleOrGone(isVisible: Boolean) {
    if (isVisible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun View.visibleOrInvisible(isVisible: Boolean) {
    if (isVisible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.INVISIBLE
    }
}

fun View.enable() {
    if (!this.isEnabled) {
        this.isEnabled = true
    }
}

fun View.disable() {
    if (this.isEnabled) {
        this.isEnabled = false
    }
}

fun View.enableOrDisable(isEnable: Boolean) {
    this.isEnabled = isEnable
}

fun TabLayout.Tab.createBadge(
    count: Int,
    textColor: Int = Color.WHITE,
    backColor: Int = Color.parseColor("#db635d")
) {
    this.orCreateBadge.apply {
        backgroundColor  = backColor
        badgeTextColor   = textColor
        horizontalOffset = -10
        number = count
    }
}

fun RecyclerView.disableViewPagerSwipe() {
    addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
        override fun onTouchEvent(view: RecyclerView, event: MotionEvent) {}
        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        override fun onInterceptTouchEvent(view: RecyclerView, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    this@disableViewPagerSwipe.parent?.requestDisallowInterceptTouchEvent(
                        true
                    )
                }
            }
            return false
        }
    })
}

fun Activity.screenHeight(): Int {
    @Suppress("DEPRECATION")
    val display = this.windowManager.defaultDisplay
    val point = Point()
    @Suppress("DEPRECATION")
    display.getSize(point)
    return point.y
}

fun Activity.screenWidth(): Int {
    @Suppress("DEPRECATION")
    val display = this.windowManager.defaultDisplay
    val point = Point()
    @Suppress("DEPRECATION")
    display.getSize(point)
    return point.x
}

fun View.toBottom(y: Int) {
    this.translationY = y.toFloat()
}

fun EditText.printError(error: String) {
    this.error = error
    this.requestFocus()
}

fun EditText.readString(): String {
    return text.toString().trim()
}

fun EditText.isEmptyString(): Boolean {
    return readString().isEmpty()
}

fun ViewPager2.runSimpleSlider(
    maxSize: Int,
    delay: Long = 3500,
    block: ( Int ) -> Unit = {}
) {
    this.postDelayed(
        {
            var currentPage = this.currentItem

            if( currentPage < maxSize - 1 ) {
                currentPage += 1
                this.currentItem = currentPage
            } else {
                currentPage = 0
                this.currentItem = 0
            }

            block( currentPage )

            this.runSimpleSlider(
                maxSize = maxSize,
                delay = delay,
                block = block
            )
        },
        delay
    )
}