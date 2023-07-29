@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package ir.farsroidx.core

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ir.farsroidx.core.additives.autoViewDataBinding

abstract class CoreFragment <VDB: ViewBinding> : Fragment() {

    protected lateinit var binding : VDB
        private set

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        if (!this::binding.isInitialized) {
            binding = autoViewDataBinding()
            onInitialized( savedInstanceState )
        }
        onReInitializing( savedInstanceState )
        return binding.root
    }

    /** After onCreate called */
    protected abstract fun onInitialized(savedInstanceState: Bundle?)

    /** It is called every time the fragment is created */
    protected open fun onReInitializing(savedInstanceState: Bundle?) {

    }

    protected fun getColorRes(@ColorRes resId: Int): Int {
        return ContextCompat.getColor(requireContext(), resId)
    }

    protected fun getStringRes(@StringRes resId: Int, vararg formatArgs: Any): String {
        return getString(resId, formatArgs)
    }

    protected fun getDrawableRes(@DrawableRes resId: Int): Drawable? {
        return ContextCompat.getDrawable(requireContext(), resId)
    }

    protected fun openKeyboard() {
        requireActivity().window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        )
    }

//    protected fun navigateTo(deepLink: Uri) {
//        findNavController().navigate(deepLink)
//    }
//
//    protected fun navigateTo(resId: Int) {
//        findNavController().navigate(resId)
//    }
//
//    protected fun navigateTo(directions: NavDirections) {
//        findNavController().navigate(directions)
//    }
//
//    protected fun navigateTo(route: String) {
//        findNavController().navigate(route)
//    }
//
//    protected fun navigateUp() {
//        findNavController().navigateUp()
//    }
}