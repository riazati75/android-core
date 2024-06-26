@file:Suppress("UNCHECKED_CAST", "DiscouragedApi")

package ir.farsroidx.core.additives

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import ir.farsroidx.core.AbstractActivity
import ir.farsroidx.core.AbstractFragment
import ir.farsroidx.core.recycler.AbstractRecyclerAdapter
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
internal fun <T: ViewDataBinding> AbstractActivity<*, *>.autoViewDataBinding(): T {

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
internal fun <T: ViewDataBinding> AbstractFragment<*, *>.autoViewDataBinding(
    inflater: LayoutInflater, container: ViewGroup? = null, attachToParent: Boolean = false
): T {

    val persistentClass : Class<T> = ( javaClass.genericSuperclass as ParameterizedType )
        .actualTypeArguments[0] as Class<T>

    val layoutName = persistentClass.simpleName.toSnakeCase().substringBeforeLast("_")

    val layoutResId = resources.getIdentifier(
        layoutName, "layout", requireActivity().packageName
    )

    return DataBindingUtil.inflate<T>(
        inflater, layoutResId, container, attachToParent
    ).apply {
        lifecycleOwner = this@autoViewDataBinding.viewLifecycleOwner
    }
}

/** Automatically sets (ViewDataBinding) using generics detection */
internal fun <T: ViewDataBinding> AbstractRecyclerAdapter<*, *>.autoViewDataBinding(
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