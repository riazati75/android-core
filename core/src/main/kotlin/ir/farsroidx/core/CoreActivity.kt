@file:Suppress("MemberVisibilityCanBePrivate", "UNUSED_PARAMETER")

package ir.farsroidx.core

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import ir.farsroidx.core.additives.autoViewDataBinding
import java.io.Serializable

abstract class CoreActivity <VDB: ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding : VDB
        private set

    protected open var isRtlDirection = true

    protected var enterAnimation = android.R.anim.fade_in
    protected var exitAnimation  = android.R.anim.fade_out

    protected var useTransitionAnimation = true

    override fun onCreate(savedInstanceState: Bundle?) {

        onBeforeInitializing(savedInstanceState)

        window.decorView.layoutDirection =
            if (isRtlDirection) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR

        super.onCreate(savedInstanceState)

        // Auto DataBinding
        binding = autoViewDataBinding()

//        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true){
//            override fun handleOnBackPressed() {
//                onBackStackPressed()
//            }
//        })

        onInitialized()
    }

    /** Before onCreate called */
    protected open fun onBeforeInitializing(savedInstanceState: Bundle?) {

    }

    /** After onCreate called */
    protected abstract fun onInitialized()

    @CallSuper
    protected open fun onBackStackPressed() {
        finish()
        runTransitionAnimation()
    }

    fun onBackPressedFromXml(view: View) {
        onBackStackPressed()
    }

    fun launchActivity(
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

            if (withFinish) {
                finish()
            }
        }

        runTransitionAnimation()
    }

    fun launchAppSettingsPage() {
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
}
