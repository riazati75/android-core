@file:Suppress("unused")

package ir.farsroidx.core.additives

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import ir.farsroidx.core.R

// TODO: Notifier ===================================================================== Notifier ===

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}

fun Context.longToast(message: String) = toast(message, Toast.LENGTH_LONG)

fun Fragment.longToast(message: String) = toast(message, Toast.LENGTH_LONG)

// TODO: Snackbar ===================================================================== Snackbar ===

fun FragmentActivity.lightSnackbar(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = initializeSnackbar(1, message, duration, animation, action, clickListener)

fun Fragment.lightSnackbar(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = requireActivity().lightSnackbar(message, duration, animation, action, clickListener)

fun FragmentActivity.graySnackbar(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = initializeSnackbar(2, message, duration, animation, action, clickListener)

fun Fragment.graySnackbar(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = requireActivity().graySnackbar(message, duration, animation, action, clickListener)

fun FragmentActivity.darkSnackbar(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = initializeSnackbar(3, message, duration, animation, action, clickListener)

fun Fragment.darkSnackbar(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = requireActivity().darkSnackbar(message, duration, animation, action, clickListener)

fun FragmentActivity.infoSnackbar(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = initializeSnackbar(4, message, duration, animation, action, clickListener)

fun Fragment.infoSnackbar(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = requireActivity().infoSnackbar(message, duration, animation, action, clickListener)

fun FragmentActivity.successSnackbar(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = initializeSnackbar(5, message, duration, animation, action, clickListener)

fun Fragment.successSnackbar(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = requireActivity().successSnackbar(message, duration, animation, action, clickListener)

fun FragmentActivity.warningSnackbar(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = initializeSnackbar(6, message, duration, animation, action, clickListener)

fun Fragment.warningSnackbar(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = requireActivity().warningSnackbar(message, duration, animation, action, clickListener)

fun FragmentActivity.errorSnackbar(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = initializeSnackbar(7, message, duration, animation, action, clickListener)

fun Fragment.errorSnackbar(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = requireActivity().errorSnackbar(message, duration, animation, action, clickListener)

private fun FragmentActivity.initializeSnackbar(
    state: Int,
    message: CharSequence,
    duration: Int,
    animation: Int,
    action: String?,
    clickListener: View.OnClickListener?,
) = Snackbar.make(
    this, this.findViewById(android.R.id.content), message, duration
).apply {

    animationMode = animation

    action?.let {
        setAction(it, clickListener)
        if (clickListener != null) {
            setDuration(Snackbar.LENGTH_INDEFINITE)
        }
    } ?: run {
        if (duration == Snackbar.LENGTH_INDEFINITE) {
            setDuration(Snackbar.LENGTH_LONG)
        }
    }

    when(state) {
        1 -> {
            setBackgroundTint(getColorResource(R.color.black))
            setTextColor(getColorResource(R.color.black))
            setActionTextColor(getColorResource(R.color.pink_500))
        }
        2 -> {
            setBackgroundTint(getColorResource(R.color.gray500))
            setTextColor(getColorResource(R.color.gray900))
            setActionTextColor(getColorResource(R.color.white))
        }
        3 -> {
            setBackgroundTint(getColorResource(R.color.black))
            setTextColor(getColorResource(R.color.white))
            setActionTextColor(getColorResource(R.color.pink_500))
        }
        4 -> {
            setBackgroundTint(getColorResource(R.color.light_blue_400))
            setTextColor(getColorResource(R.color.light_blue_900))
            setActionTextColor(getColorResource(R.color.white))
        }
        5 -> {
            setBackgroundTint(getColorResource(R.color.green_500))
            setTextColor(getColorResource(R.color.white))
            setActionTextColor(getColorResource(R.color.light_green_900))
        }
        6 -> {
            setBackgroundTint(getColorResource(R.color.orange_600))
            setTextColor(getColorResource(R.color.black))
            setActionTextColor(getColorResource(R.color.white))
        }
        7 -> {
            setBackgroundTint(getColorResource(R.color.red_400))
            setTextColor(getColorResource(R.color.white))
            setActionTextColor(getColorResource(R.color.yellow_500))
        }
    }

}.show()

fun FragmentActivity.coloredSnackbar(
    message: CharSequence,
    messageColor: Int = Color.BLACK,
    action: String? = null,
    actionColor: Int = Color.BLUE,
    backgroundColor: Int = Color.WHITE,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    clickListener: View.OnClickListener? = null,
) = Snackbar.make(
    this, this.findViewById(android.R.id.content), message, duration
).apply {

    animationMode = animation

    setDuration(duration)
    setTextColor(messageColor)
    setActionTextColor(actionColor)
    setBackgroundTint(backgroundColor)

    action?.let {
        setAction(it, clickListener)
    } ?: run {
        if (duration == Snackbar.LENGTH_INDEFINITE) {
            setDuration(Snackbar.LENGTH_LONG)
        }
    }

}.show()

fun Fragment.coloredSnackbar(
    message: CharSequence,
    messageColor: Int = Color.BLACK,
    action: String? = null,
    actionColor: Int = Color.BLUE,
    backgroundColor: Int = Color.WHITE,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    clickListener: View.OnClickListener? = null,
) = requireActivity().coloredSnackbar(
    message, messageColor, action, actionColor, backgroundColor, duration, animation, clickListener
)