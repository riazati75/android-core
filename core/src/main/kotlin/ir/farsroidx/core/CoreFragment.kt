@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package ir.farsroidx.core

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import ir.farsroidx.core.additives.autoViewDataBinding
import kotlinx.coroutines.Job

abstract class CoreFragment <VDB: ViewBinding> : Fragment() {

    private lateinit var _binding : VDB

    protected val binding : VDB by lazy { _binding }

    protected var activeJob: Job? = null

    internal var pendingRequest: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        if (!this::_binding.isInitialized) {
            _binding = autoViewDataBinding()
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

    protected fun binding(block: VDB.() -> Unit) = binding.apply {
        block.invoke(this)
    }

    protected fun navigate(deepLink: Uri) {
        findNavController().navigate(deepLink)
    }

    protected fun navigate(resId: Int) {
        findNavController().navigate(resId)
    }

    protected fun navigate(directions: NavDirections) {
        findNavController().navigate(directions)
    }

    protected fun navigate(route: String) {
        findNavController().navigate(route)
    }

    protected fun navigateUp() {
        findNavController().navigateUp()
    }

    open fun onFragmentResult(requestCode: Int, bundle: Bundle) {}

    fun navigate(navDirection: NavDirections, requestCode: Int = -1) {
        navigate(navDirection.actionId, navDirection.arguments, requestCode)
    }

    fun navigate(
        navDirection: NavDirections,
        navOptions: NavOptions?,
        requestCode: Int = -1
    ) {
        navigate(navDirection.actionId, navDirection.arguments, navOptions, null, requestCode)
    }

    fun navigate(
        navDirection: NavDirections,
        navigatorExtras: Navigator.Extras?,
        requestCode: Int = -1
    ) {
        navigate(navDirection.actionId, navDirection.arguments, null, navigatorExtras, requestCode)
    }

    fun navigate(@IdRes navDirection: Int, requestCode: Int) {
        navigate(navDirection, null, requestCode)
    }

    fun navigate(@IdRes navDirection: Int, bundle: Bundle?, requestCode: Int) {
        navigate(navDirection, bundle, null, requestCode)
    }

    fun navigate(
        @IdRes navDirection: Int,
        bundle: Bundle?,
        navOptions: NavOptions?,
        requestCode: Int
    ) {
        navigate(navDirection, bundle, navOptions, null, requestCode)
    }

    fun navigate(
        @IdRes navDirection: Int,
        bundle: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?,
        requestCode: Int
    ) {
        val theActualBundle = bundle ?: Bundle()
        extractPendingRequest(theActualBundle, requestCode)
        findNavController().navigate(navDirection, theActualBundle, navOptions, navigatorExtras)
    }

    private fun extractPendingRequest(bundle: Bundle, requestCode: Int) {
        pendingRequest = if (requestCode > -1) {
            bundle.putInt(CoreActivity.FRAGMENT_REQUEST_CODE, requestCode)
            requestCode
        } else {
            bundle.getInt(CoreActivity.FRAGMENT_REQUEST_CODE, -1)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(PENDING_REQUEST, pendingRequest)
    }

    companion object {
        private const val PENDING_REQUEST = "PENDING_REQUEST"
    }
}