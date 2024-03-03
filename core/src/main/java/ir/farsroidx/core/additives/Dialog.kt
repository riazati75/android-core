@file:Suppress("DEPRECATION", "unused")

package ir.farsroidx.core.additives

import android.app.ProgressDialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import ir.farsroidx.core.R

// TODO: Dialog ========================================================================= Dialog ===

fun Fragment.alertDialog(
    @StyleRes styleResId: Int? = R.style.Theme_Farsroidx_Dialog,
    invoker: AlertDialog.Builder.() -> Unit
) = requireContext().alertDialog(styleResId, invoker)

fun Context.alertDialog(
    @StyleRes styleResId: Int? = R.style.Theme_Farsroidx_Dialog,
    invoker: AlertDialog.Builder.() -> Unit
) = if (styleResId == null) {
    AlertDialog.Builder(this)
} else {
    AlertDialog.Builder(this, styleResId)
}.apply(invoker).create()

fun AlertDialog.showWithCenteredButtons(
    isVerticalAlignmentButton: Boolean = false
) {

    this.show().apply {

        val positive = getButton(AlertDialog.BUTTON_POSITIVE)
        val negative = getButton(AlertDialog.BUTTON_NEGATIVE)
        val neutral  = getButton(AlertDialog.BUTTON_NEUTRAL)

        (positive.parent as? LinearLayout)?.apply {

            gravity = Gravity.CENTER_HORIZONTAL

            val leftSpacer = getChildAt(1)

            leftSpacer?.visibility = View.GONE

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                if (isVerticalAlignmentButton) {
                    orientation = LinearLayout.VERTICAL
                }
            }

            layoutParams.weight  = 1F
            layoutParams.gravity = Gravity.CENTER

            positive.layoutParams = layoutParams
            negative.layoutParams = layoutParams
            neutral.layoutParams  = layoutParams
        }
    }
}

fun Fragment.progressDialog(
    message: CharSequence = "لطفأ منتظر بمانید ..."
) = requireContext().progressDialog(message)

fun Context.progressDialog(
    message: CharSequence = "لطفأ منتظر بمانید ..."
) = ProgressDialog(
    this, R.style.Theme_Farsroidx_Dialog
).apply {
    progress = 0
    isIndeterminate = true
    setMessage(message)
    setCancelable(false)
}