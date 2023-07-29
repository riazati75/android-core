@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package ir.farsroidx.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class CoreViewModel2 <VS: Any> : ViewModel() {

    protected var onViewStateChange: (VS) -> Unit = {}

    fun setOnViewStateChanged(onChange: (VS) -> Unit) {
        onViewStateChange = onChange
    }

    suspend fun doInIOThread(block: () -> Unit) {
        withContext(Dispatchers.IO) {
            block()
        }
    }

    suspend fun doInMainThread(block: () -> Unit) {
        withContext(Dispatchers.Main) {
            block()
        }
    }

    fun viewModelScope(
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(Dispatchers.IO) {
        block()
    }
}