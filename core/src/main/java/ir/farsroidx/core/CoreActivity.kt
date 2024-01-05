@file:Suppress("MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "DEPRECATION", "unused")

package ir.farsroidx.core

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import ir.farsroidx.core.additives.autoViewDataBinding
import ir.farsroidx.core.additives.getPersianDateTime
import ir.farsroidx.core.additives.makeViewModel
import ir.farsroidx.core.additives.progressDialog
import ir.farsroidx.core.model.SerializedData
import kotlinx.coroutines.Job
import java.io.Serializable

abstract class CoreActivity <VDB: ViewDataBinding, VM: CoreViewModel> : AppCompatActivity() {

    companion object {

        private const val PENDING_REQUESTS       = "PENDING_REQUESTS"
        private const val DIALOG_NAVIGATOR       = "DIALOG"
        private const val FRAGMENT_NAVIGATOR     = "FRAGMENT"

        internal const val FRAGMENT_REQUEST_CODE = "fragment:requestCode"

    }

    protected lateinit var binding : VDB
        private set

    protected lateinit var viewModel: VM
        private set

    protected open var isRtlDirection = true

    protected var enterAnimation = android.R.anim.fade_in
    protected var exitAnimation  = android.R.anim.fade_out

    protected var useTransitionAnimation = true

    protected var activeJob: Job? = null

    private lateinit var progressDialog: ProgressDialog

    private var pendingRequests = HashMap<Int, Bundle?>()

    private var navHostFragmentIdCache: Int = -1

    private var backStackChangeListener: FragmentManager.OnBackStackChangedListener? = null

    private var destinationChangeListener: NavController.OnDestinationChangedListener? = null

    private var wasPreviouslyShowingDialog = false

    override fun onCreate(savedInstanceState: Bundle?) {

        onBeforeInitializing(savedInstanceState)

        window.decorView.layoutDirection =
            if (isRtlDirection) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR

        super.onCreate(savedInstanceState)

        progressDialog = onCreateProgressDialog()

        @Suppress("UNCHECKED_CAST")
        savedInstanceState?.let {
            if (it.containsKey(PENDING_REQUESTS)) {
                pendingRequests = (
                    it.getSerializable(PENDING_REQUESTS) as SerializedData<HashMap<Int, Bundle?>>
                ).serialized
            }
        }

        // Auto Make ViewModel
        viewModel = makeViewModel()

        // Auto DataBinding
        binding = autoViewDataBinding()

        // Setup ViewStateChange
        viewModel.setOnViewStateChanged(this, ::onViewStateChanged)

//        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true){
//            override fun handleOnBackPressed() {
//                onBackStackPressed()
//            }
//        })

        binding.onInitialized()
    }

    /** Before onCreate called */
    protected open fun onBeforeInitializing(savedInstanceState: Bundle?) {

    }

    /** After onCreate called */
    protected abstract fun VDB.onInitialized()

    @CallSuper
    protected open fun onBackStackPressed() {
        finish()
        runTransitionAnimation()
    }

    fun onBackPressedFromXml(view: View) {
        onBackStackPressed()
    }

    fun startActivity(
        clazz: Class<*>,
        extras: Map<String, Any>? = null,
        intent: (Intent) -> Unit = {},
        withFinish: Boolean = false,
    ) {
        Intent(this, clazz).apply {
            extras?.forEach { (key, value) ->
                when(value) {
                    is Boolean      -> { this.putExtra(key, value) }
                    is Byte         -> { this.putExtra(key, value) }
                    is Char         -> { this.putExtra(key, value) }
                    is Short        -> { this.putExtra(key, value) }
                    is Int          -> { this.putExtra(key, value) }
                    is Long         -> { this.putExtra(key, value) }
                    is Float        -> { this.putExtra(key, value) }
                    is Double       -> { this.putExtra(key, value) }
                    is String       -> { this.putExtra(key, value) }
                    is CharSequence -> { this.putExtra(key, value) }
                    is Serializable -> { this.putExtra(key, value) }
                    else -> {
                        throw Exception(
                            "Type of key: $key, value: $value not supported!"
                        )
                    }
                }
            }

            intent( this )

            startActivity(this)

            if (withFinish) finish()
        }

        runTransitionAnimation()
    }

    fun startAppSettings() {

        startActivity(
            Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts(
                    "package", packageName, null
                )
            )
        )

        runTransitionAnimation()
    }

    private fun runTransitionAnimation() {
        if (useTransitionAnimation) {
            overridePendingTransition(
                enterAnimation, exitAnimation
            )
        }
    }

    protected fun getColorRes(@ColorRes resId: Int): Int {
        return ContextCompat.getColor(this, resId)
    }

    protected fun getStringRes(@StringRes resId: Int): String {
        return getString(resId)
    }

    protected fun getDrawableRes(@DrawableRes resId: Int): Drawable? {
        return ContextCompat.getDrawable(this, resId)
    }

    protected fun binding(block: VDB.() -> Unit) = binding.apply {
        block.invoke(this)
    }

    private fun attachDestinationChangeListener() {

        if (navHostFragmentIdCache == -1) return

        destinationChangeListener = NavController
            .OnDestinationChangedListener { _, destination, arguments ->

                if (destination.navigatorName == DIALOG_NAVIGATOR) {
                    wasPreviouslyShowingDialog = true

                } else if (
                    destination.navigatorName == FRAGMENT_NAVIGATOR && wasPreviouslyShowingDialog
                ) {

                    wasPreviouslyShowingDialog = false

                    backStackChangeListener?.onBackStackChanged()
                }

                arguments?.getInt(FRAGMENT_REQUEST_CODE, -1)
                    ?.takeIf {
                        it > -1
                    }
                    ?.also {
                        pendingRequests[it] = null
                    }

            }.also {
                findNavController(navHostFragmentIdCache).addOnDestinationChangedListener(it)
            }
    }

    private fun attachBackStackChangeListener() {

        if (navHostFragmentIdCache == -1) return

        backStackChangeListener = FragmentManager.OnBackStackChangedListener {

            supportFragmentManager.findFragmentById(navHostFragmentIdCache)?.let {

                (it.childFragmentManager.primaryNavigationFragment as CoreFragment<*, *>).apply {
                    takeIf { coreFragment ->
                        coreFragment.pendingRequest > -1
                    }
                    ?.takeIf { coreFragment ->
                        pendingRequests[coreFragment.pendingRequest] != null
                    }
                    ?.also { coreFragment ->
                        coreFragment.onFragmentResult(
                            coreFragment.pendingRequest,
                            pendingRequests[coreFragment.pendingRequest]!!
                        )
                    }
                    ?.also { coreFragment ->
                        pendingRequests.remove(coreFragment.pendingRequest)
                    }
                }
            }

        }.also {
            supportFragmentManager.findFragmentById(navHostFragmentIdCache)
                ?.childFragmentManager
                ?.addOnBackStackChangedListener(it)
        }
    }

    internal fun setBundle(requestCode: Int, bundle: Bundle) {
        pendingRequests[requestCode] = bundle
    }

    fun navigate(navDirection: NavDirections, requestCode: Int = -1) {
        navigate(navDirection.actionId, navDirection.arguments, requestCode)
    }

    fun navigate(
        navDirection: NavDirections,
        navOptions: NavOptions?,
        requestCode: Int = -1
    ) = navigate(
        navDirection.actionId, navDirection.arguments, navOptions, null, requestCode
    )

    fun navigate(
        navDirection: NavDirections,
        navigatorExtras: Navigator.Extras?,
        requestCode: Int = -1
    ) = navigate(
        navDirection.actionId, navDirection.arguments, null, navigatorExtras, requestCode
    )

    fun navigate(@IdRes navDirection: Int, requestCode: Int) =
        navigate(navDirection, null, requestCode)

    fun navigate(@IdRes navDirection: Int, bundle: Bundle?, requestCode: Int) =
        navigate(navDirection, bundle, null, requestCode)

    fun navigate(
        @IdRes navDirection: Int,
        bundle: Bundle?,
        navOptions: NavOptions?,
        requestCode: Int
    ) = navigate(
        navDirection, bundle, navOptions, null, requestCode
    )

    fun navigate(
        @IdRes navDirection: Int,
        bundle: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?,
        requestCode: Int
    ) {

        if (navHostFragmentIdCache == -1) return

        supportFragmentManager.findFragmentById(navHostFragmentIdCache)?.let {
            (it.childFragmentManager.primaryNavigationFragment as CoreFragment<*, *>).apply {
                navigate(navDirection, bundle, navOptions, navigatorExtras, requestCode)
            }
        }
    }

    fun initNavHostFragmentId(@IdRes navHostId: Int) {
        navHostFragmentIdCache = navHostId
    }

    fun updateNavHostFragmentId(@IdRes navHostId: Int) {
        navHostFragmentIdCache = navHostId
        reattach()
    }

    private fun reattach() {

        if (navHostFragmentIdCache == -1) return

        detachBackStackChangeListener()
        detachDestinationChangeListener()
        attachBackStackChangeListener()
        attachDestinationChangeListener()
    }

    private fun detachBackStackChangeListener() {

        if (navHostFragmentIdCache == -1) return

        backStackChangeListener?.let {
            supportFragmentManager.findFragmentById(
                navHostFragmentIdCache
            )?.childFragmentManager?.removeOnBackStackChangedListener(it)
        }
    }

    private fun detachDestinationChangeListener() {

        if (navHostFragmentIdCache == -1) return

        destinationChangeListener?.let {
            findNavController(navHostFragmentIdCache)
                .removeOnDestinationChangedListener(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putSerializable(
            PENDING_REQUESTS, SerializedData(pendingRequests)
        )
    }

    protected open fun onCreateProgressDialog(): ProgressDialog {
        return progressDialog()
    }

    protected fun showProgressDialog() {
        if (!progressDialog.isShowing) {
            progressDialog.show()
        }
    }

    protected fun hideProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (navHostFragmentIdCache != -1) {
            findNavController(navHostFragmentIdCache)
                .handleDeepLink(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        if (navHostFragmentIdCache == -1) return

        attachDestinationChangeListener()
        attachBackStackChangeListener()
    }

    override fun onStop() {
        super.onStop()

        activeJob?.let {
            if (it.isActive && !it.isCompleted && !it.isCancelled) {
                it.cancel()
            }
        }

        if (navHostFragmentIdCache == -1) return

        detachBackStackChangeListener()
        detachDestinationChangeListener()
    }

    private fun onViewStateChanged(viewState: CoreViewState) {
        binding.onViewStateChanged(viewState)
    }

    open fun VDB.onViewStateChanged(viewState: CoreViewState) {}
}
