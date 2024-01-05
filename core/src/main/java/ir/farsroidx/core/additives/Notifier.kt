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

fun Context.toast(message: CharSequence, duration: Int  = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}

fun Context.toastLong(message: CharSequence)  = toast(message, Toast.LENGTH_LONG)

fun Fragment.toastLong(message: CharSequence) = toast(message, Toast.LENGTH_LONG)

// TODO: Snackbar ===================================================================== Snackbar ===

private enum class SnackbarState {
    LIGHT, GRAY, DARK, INFO, SUCCESS, WARNING, ERROR
}

fun FragmentActivity.snackbarLight(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = initializeSnackbar(SnackbarState.LIGHT, message, duration, animation, action, clickListener)

fun Fragment.snackbarLight(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = requireActivity().snackbarLight(message, duration, animation, action, clickListener)

fun FragmentActivity.snackbarGray(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = initializeSnackbar(SnackbarState.GRAY, message, duration, animation, action, clickListener)

fun Fragment.snackbarGray(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = requireActivity().snackbarGray(message, duration, animation, action, clickListener)

fun FragmentActivity.snackbarDark(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = initializeSnackbar(SnackbarState.DARK, message, duration, animation, action, clickListener)

fun Fragment.snackbarDark(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = requireActivity().snackbarDark(message, duration, animation, action, clickListener)

fun FragmentActivity.snackbarInfo(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = initializeSnackbar(SnackbarState.INFO, message, duration, animation, action, clickListener)

fun Fragment.snackbarInfo(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = requireActivity().snackbarInfo(message, duration, animation, action, clickListener)

fun FragmentActivity.snackbarSuccess(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = initializeSnackbar(SnackbarState.SUCCESS, message, duration, animation, action, clickListener)

fun Fragment.snackbarSuccess(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = requireActivity().snackbarSuccess(message, duration, animation, action, clickListener)

fun FragmentActivity.snackbarWarning(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = initializeSnackbar(SnackbarState.WARNING, message, duration, animation, action, clickListener)

fun Fragment.snackbarWarning(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = requireActivity().snackbarWarning(message, duration, animation, action, clickListener)

fun FragmentActivity.snackbarError(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = initializeSnackbar(SnackbarState.ERROR, message, duration, animation, action, clickListener)

fun Fragment.snackbarError(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    animation: Int = Snackbar.ANIMATION_MODE_SLIDE,
    action: String? = null,
    clickListener: View.OnClickListener? = null,
) = requireActivity().snackbarError(message, duration, animation, action, clickListener)

private fun FragmentActivity.initializeSnackbar(
    state: SnackbarState,
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
    } ?: run {
        if (duration == Snackbar.LENGTH_INDEFINITE) {
            setDuration(Snackbar.LENGTH_LONG)
        }
    }

    when(state) {
        SnackbarState.LIGHT -> {
            setBackgroundTint(getColorResource(R.color.black))
            setTextColor(getColorResource(R.color.black))
            setActionTextColor(getColorResource(R.color.pink_500))
        }
        SnackbarState.GRAY -> {
            setBackgroundTint(getColorResource(R.color.gray500))
            setTextColor(getColorResource(R.color.gray900))
            setActionTextColor(getColorResource(R.color.white))
        }
        SnackbarState.DARK -> {
            setBackgroundTint(getColorResource(R.color.black))
            setTextColor(getColorResource(R.color.white))
            setActionTextColor(getColorResource(R.color.pink_500))
        }
        SnackbarState.INFO -> {
            setBackgroundTint(getColorResource(R.color.light_blue_400))
            setTextColor(getColorResource(R.color.light_blue_900))
            setActionTextColor(getColorResource(R.color.white))
        }
        SnackbarState.SUCCESS -> {
            setBackgroundTint(Color.parseColor("#198754"))
            setTextColor(getColorResource(R.color.white))
            setActionTextColor(getColorResource(R.color.light_green_900))
        }
        SnackbarState.WARNING -> {
            setBackgroundTint(getColorResource(R.color.orange_600))
            setTextColor(getColorResource(R.color.black))
            setActionTextColor(getColorResource(R.color.white))
        }
        SnackbarState.ERROR -> {
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