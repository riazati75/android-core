@file:Suppress("UNCHECKED_CAST", "DiscouragedApi")

package ir.farsroidx.core.additives

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import ir.farsroidx.core.CoreActivity
import ir.farsroidx.core.CoreFragment
import ir.farsroidx.core.CoreSheetDialog
import ir.farsroidx.core.recycler.CoreRecyclerViewAdapter
import java.lang.reflect.ParameterizedType
import java.util.Locale

// TODO: ViewBinding =============================================================== ViewBinding ===

/** Convert a camel case string to snake case. */
private val camelRegex = "(?<=[a-zA-Z])[A-Z]".toRegex()

internal fun String.toSnakeCase(): String {
    return camelRegex.replace(this) {
        "_${it.value}"
    }.lowercase(
        Locale.getDefault()
    )
}

/** Automatically sets (ViewDataBinding) using generics detection */
internal fun <T: ViewDataBinding> CoreActivity<*>.autoViewDataBinding(): T {

    val persistentClass : Class<T> = ( javaClass.genericSuperclass as ParameterizedType )
        .actualTypeArguments[0] as Class<T>

    val layoutName = persistentClass.simpleName.toSnakeCase().substringBeforeLast("_")

    val layoutResId = resources.getIdentifier(layoutName, "layout", packageName)

    return DataBindingUtil.inflate<T>(layoutInflater, layoutResId, null, false)
        .apply {
            lifecycleOwner = this@autoViewDataBinding
            setContentView( root )
        }
}

/** Automatically sets (ViewDataBinding) using generics detection */
internal fun <T: ViewDataBinding> CoreSheetDialog<*>.autoViewDataBinding(): T {

    val persistentClass : Class<T> = ( javaClass.genericSuperclass as ParameterizedType )
        .actualTypeArguments[0] as Class<T>

    val layoutName = persistentClass.simpleName.toSnakeCase().substringBeforeLast("_")

    val layoutResId = context.resources.getIdentifier(layoutName, "layout", context.packageName)

    return DataBindingUtil.inflate<T>(layoutInflater, layoutResId, null, false)
        .apply {
            lifecycleOwner = this@autoViewDataBinding
            setContentView( root )
        }
}

/** Automatically sets (ViewDataBinding) using generics detection */
internal fun <T: ViewDataBinding> CoreFragment<*>.autoViewDataBinding(): T {

    val persistentClass : Class<T> = ( javaClass.genericSuperclass as ParameterizedType )
        .actualTypeArguments[0] as Class<T>

    val layoutName = persistentClass.simpleName.toSnakeCase().substringBeforeLast("_")

    val layoutResId = resources.getIdentifier(
        layoutName, "layout", requireActivity().packageName
    )

    return DataBindingUtil.inflate<T>(layoutInflater, layoutResId, null, false)
        .apply {
            lifecycleOwner = this@autoViewDataBinding.viewLifecycleOwner
        }
}

/** Automatically sets (ViewDataBinding) using generics detection */
internal fun <T: ViewDataBinding> CoreRecyclerViewAdapter<*, *>.autoViewDataBinding(
    context: Context, layoutInflater: LayoutInflater,
    parent: ViewGroup? = null, attachToParent: Boolean = false
): T {

    val persistentClass : Class<T> = ( javaClass.genericSuperclass as ParameterizedType )
        .actualTypeArguments[0] as Class<T>

    val layoutName = persistentClass.simpleName.toSnakeCase().substringBeforeLast("_")

    val layoutResId = context.resources.getIdentifier(
        layoutName, "layout", context.packageName
    )

    return DataBindingUtil.inflate(layoutInflater, layoutResId, parent, attachToParent)
}