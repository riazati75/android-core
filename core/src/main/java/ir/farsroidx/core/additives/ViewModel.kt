@file:Suppress("UNCHECKED_CAST")

package ir.farsroidx.core.additives

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import ir.farsroidx.core.AbstractActivity
import ir.farsroidx.core.AbstractFragment
import ir.farsroidx.core.AbstractViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.ParameterizedType

// TODO: ViewModel =================================================================== ViewModel ===

private fun <T: ViewModel> ViewModelStoreOwner.getViewModelInstance(genericIndex: Int): Class<T> {
    return (javaClass.genericSuperclass as ParameterizedType)
        .actualTypeArguments[genericIndex] as Class<T>
}

internal fun <T : AbstractViewModel> AbstractActivity<*, T>.makeViewModel(): T {
    return ViewModelProvider(this)[getViewModelInstance(1)]
}

internal fun <T : AbstractViewModel> AbstractFragment<*, T>.makeViewModel(): T {
    return ViewModelProvider(this)[getViewModelInstance(1)]
}

fun ViewModel.postWithDelay(
    delay: Long, block: () -> Unit
) = viewModelScope.launch(Dispatchers.IO) {
    delay(delay)
    withContext(Dispatchers.Main) {
        block()
    }
}