@file:Suppress("unused")

package ir.farsroidx.core.additives

import android.text.TextUtils
import android.widget.TextView

fun TextView.setMarqueeRepeatForEver(isStarted: Boolean) {
    ellipsize                     = TextUtils.TruncateAt.MARQUEE
    isHorizontalFadingEdgeEnabled = true
    setHorizontallyScrolling(true)
    marqueeRepeatLimit            = if (isStarted) -1 else 0
    isSingleLine                  = true
    isFocusable                   = true
    freezesText                   = true
    isSelected                    = isStarted
}