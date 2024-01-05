@file:Suppress("UNCHECKED_CAST")

package ir.farsroidx.core.additives

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import ir.farsroidx.core.CoreActivity
import ir.farsroidx.core.CoreFragment
import ir.farsroidx.core.CoreViewModel
import java.lang.reflect.ParameterizedType

// TODO: ViewModel =================================================================== ViewModel ===

private fun <T: ViewModel> ViewModelStoreOwner.getViewModelInstance(genericIndex: Int): Class<T> {
    return (javaClass.genericSuperclass as ParameterizedType)
        .actualTypeArguments[genericIndex] as Class<T>
}

internal fun <T : CoreViewModel> CoreActivity<*, T>.makeViewModel(): T {
    return ViewModelProvider(this)[getViewModelInstance(1)]
}

internal fun <T : CoreViewModel> CoreFragment<*, T>.makeViewModel(): T {
    return ViewModelProvider(this)[getViewModelInstance(1)]
}